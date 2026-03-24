package com.UrbanVogue.admin.orderHandling.service;

import com.UrbanVogue.admin.addProduct.entity.Product;
import com.UrbanVogue.admin.addProduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Integer  checkAvailability(Long id, Integer qty) {
        Product product = getProduct(id);
        if(product.getNumberOfPieces()>=qty) {
            return qty;
        }
        return null;
    }
     public String getProductName(Long id)
     {
         Product product = getProduct(id);
         return  product.getName();
     }
    public double getPrice(Long id) {
        Product product = getProduct(id);
        return product.getPrice();
    }

    public void reduceStock(Long id, Integer qty) {
        Product product = getProduct(id);

        product.setNumberOfPieces(product.getNumberOfPieces() - qty);
        productRepository.save(product);
    }
}