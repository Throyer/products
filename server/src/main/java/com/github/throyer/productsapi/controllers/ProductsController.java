package com.github.throyer.productsapi.controllers;

import com.github.throyer.productsapi.domain.entities.Product;
import com.github.throyer.productsapi.domain.services.FindProductService;
import com.github.throyer.productsapi.domain.services.FindProductService.SearchProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private FindProductService service;

    @GetMapping
    public Page<Product> index(Pageable pageable, SearchProduct search) {
        return service.find(pageable, search);
    }
}
