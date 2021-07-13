package com.github.throyer.productsapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import com.github.throyer.productsapi.domain.entities.Technology;
import com.github.throyer.productsapi.domain.repositories.TechnologyRepository;
import com.github.throyer.productsapi.domain.shared.Page;
import com.github.throyer.productsapi.utils.Predicates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/technologies")
public class TechnologiesController {
    @Autowired
    private TechnologyRepository repository;

    @GetMapping
    public Page<Technology> index(Pageable pageable, Optional<String> name) {
        
        Specification<Technology> where = (technology, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            name.ifPresent(search -> predicates.add(Predicates.like(builder, technology.get("name"), search)));
                        
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return Page.of(repository.findAll(where, pageable));
    }
}
