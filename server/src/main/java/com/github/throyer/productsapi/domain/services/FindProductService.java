package com.github.throyer.productsapi.domain.services;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.github.throyer.productsapi.domain.entities.Product;
import com.github.throyer.productsapi.domain.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FindProductService {

    private static String REPLACES = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
    private static String MATCHES = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";
    
    @Autowired
    private ProductRepository repository;

    public Page<Product> find(Pageable pageable, SearchProduct search) {

        Specification<Product> where = (product, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            search
                .getName()
                    .ifPresent(name -> 
                        predicates.add(like(builder, product.get("name"), name)));
            
            if (!search.getTechnologies().isEmpty()) {

                var technology = product.join("technologies");

                predicates.add(builder.or(search
                    .getTechnologies()
                        .stream()
                            .map(name -> like(builder, technology.get("name"), name))
                                .toList()
                                    .toArray(new Predicate[search.getTechnologies().size()])));
            }
            
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return repository.findAll(where, pageable);
    }

    public class SearchProduct {
        private String name;
        private List<String> technologies = new ArrayList<>();
        
        public Optional<String> getName() {
            return Optional.ofNullable(name);            
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getTechnologies() {
            return technologies;
        }

        public void setTechnologies(List<String> technologies) {
            this.technologies = technologies;
        }
        
        
    }

    private static Predicate like(CriteriaBuilder builder, Expression<String> expression, String value) {
        var lower = Normalizer.normalize(value.toLowerCase().trim(), Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return builder.like(replace(builder, expression), "%" + lower + "%");
    }

    public static Expression<String> replace(
        CriteriaBuilder builder,
        Expression<String> path
    ) {
        return builder
            .function(
                "translate",
                String.class,
                builder.lower(path),
                builder.literal(REPLACES),
                builder.literal(MATCHES)
            );
    }
}
