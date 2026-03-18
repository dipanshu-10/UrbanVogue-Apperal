package com.UrbanVogue.admin.addProduct.repository;

import com.UrbanVogue.admin.addProduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}