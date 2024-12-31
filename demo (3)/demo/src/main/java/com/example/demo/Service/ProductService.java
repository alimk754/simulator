package com.example.demo.Service;

import com.example.demo.Repos.Product_repo;
import com.example.demo.classes.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private Product_repo productRepo;
    public Products saveProduct(Products product) {
        return productRepo.save(product);
    }

    public Optional<Products> getProductById(int id) {
        return productRepo.findById(id);
    }

    public List<Products> getAllProducts() {
        return productRepo.findAll();
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }
}
