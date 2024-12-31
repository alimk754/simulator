package com.example.demo.Repos;

import com.example.demo.classes.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Product_repo extends JpaRepository<Products,Integer> {
}
