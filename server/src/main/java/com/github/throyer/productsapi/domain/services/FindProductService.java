package com.github.throyer.productsapi.domain.services;

import static com.github.throyer.productsapi.domain.shared.Page.of;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import com.github.throyer.productsapi.domain.entities.Product;
import com.github.throyer.productsapi.domain.repositories.ProductRepository;
import com.github.throyer.productsapi.domain.shared.Page;
import com.github.throyer.productsapi.utils.Predicates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FindProductService {
   
    @Autowired
    private ProductRepository repository;

    public Page<Product> find(Pageable pageable, SearchProduct search) {

        Specification<Product> where = (product, query, builder) -> {

            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            search
                .getName()
                    .ifPresent(name -> 
                        predicates.add(Predicates.like(builder, product.get("name"), name)));
            
            if (!search.getTechnologies().isEmpty()) {

                var technology = product.join("technologies");

                predicates.add(builder.or(search
                    .getTechnologies()
                        .stream()
                            .map(name -> Predicates.like(builder, technology.get("name"), name))
                                .toList()
                                    .toArray(new Predicate[search.getTechnologies().size()])));
            }
            
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return of(repository.findAll(where, pageable));
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
}
