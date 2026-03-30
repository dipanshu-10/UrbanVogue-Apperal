package com.UrbanVogue.admin.addProduct.repository;//package com.UrbanVogue.admin.addProduct.repository;
//
//import com.UrbanVogue.admin.addProduct.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByCategoryIgnoreCase(String category);
//}


import com.UrbanVogue.admin.addProduct.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Correct method with Spring Pageable
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);
}