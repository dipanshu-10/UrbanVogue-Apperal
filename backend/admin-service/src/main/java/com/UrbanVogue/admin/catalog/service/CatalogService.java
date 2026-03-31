package com.UrbanVogue.admin.catalog.service;
import com.UrbanVogue.admin.addProduct.entity.Product;
import com.UrbanVogue.admin.addProduct.repository.ProductRepository;
import com.UrbanVogue.admin.catalog.dto.CatalogProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    @Autowired
    private ProductRepository productRepository;

    // Fetching  products with pagination and sorting expensive first
    // Cache is applied only when page = 0
    @Cacheable(value = "productsCache", key = "'page0'", condition = "#page == 0")
    public List<CatalogProductDTO> getProducts(int page, int size) {

        // Sorting by price in descending order (expensive products first)
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").descending());

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.getContent()
                .stream()
                .map(product -> {
                    CatalogProductDTO dto = new CatalogProductDTO();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());
                    dto.setImageUrl(product.getImageUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Search products by category with pagination no caching applied
    public List<CatalogProductDTO> searchByCategory(String category, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("price").descending());

        Page<Product> productPage =
                productRepository.findByCategoryIgnoreCase(category, pageable);

        return productPage.getContent()
                .stream()
                .map(product -> {
                    CatalogProductDTO dto = new CatalogProductDTO();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());
                    dto.setImageUrl(product.getImageUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Fetch product by ID (no change required)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}













//package com.UrbanVogue.admin.catalog.service;
//
//import com.UrbanVogue.admin.addProduct.entity.Product;
//import com.UrbanVogue.admin.addProduct.repository.ProductRepository;
//import com.UrbanVogue.admin.catalog.dto.CatalogProductDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CatalogService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    // for the explore section we have this methode
//    public List<CatalogProductDTO> getProducts() {
//        return productRepository.findAll()
//                .stream()
//                .map(product -> {
//                    CatalogProductDTO dto = new CatalogProductDTO();
//                    dto.setId(product.getId());
//                    dto.setName(product.getName());
//                    dto.setPrice(product.getPrice());
//                    dto.setImageUrl(product.getImageUrl());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//   //  for fetching the products by the category
//   public List<CatalogProductDTO> searchByCategory(String category) {
//
//       List<Product> products = productRepository.findByCategoryIgnoreCase(category);
//
//       return products.stream()
//               .map(product -> {
//                   CatalogProductDTO dto = new CatalogProductDTO();
//                   dto.setId(product.getId());
//                   dto.setName(product.getName());
//                   dto.setPrice(product.getPrice());
//                   dto.setImageUrl(product.getImageUrl());
//                   return dto;
//               })
//               .collect(Collectors.toList());
//   }
//    // for fetching the products details by id
//    public Product getProductById(Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//}