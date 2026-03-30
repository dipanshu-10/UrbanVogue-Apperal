//package com.UrbanVogue.admin.catalog.controller;
//
//import com.UrbanVogue.admin.catalog.dto.CatalogProductDTO;
//import com.UrbanVogue.admin.catalog.service.CatalogService;
//import com.UrbanVogue.admin.addProduct.entity.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/catalog")
//public class CatalogController {
//
//    @Autowired
//    private CatalogService catalogService;
//
//    // Explore section
//    @GetMapping("/getProducts")
//    public List<CatalogProductDTO> getProducts() {
//        return catalogService.getProducts();
//    }
//    // get products by search
//    @GetMapping("/search")
//    public List<CatalogProductDTO> searchByCategory(
//            @RequestParam String category
//    ) {
//        return catalogService.searchByCategory(category);
//    }
//    // Product detail
//    @GetMapping("/getProducts/{id}")
//    public Product getProductById(@PathVariable Long id) {
//        return catalogService.getProductById(id);
//    }
//}


package com.UrbanVogue.admin.catalog.controller;

import com.UrbanVogue.admin.catalog.dto.CatalogProductDTO;
import com.UrbanVogue.admin.catalog.service.CatalogService;
import com.UrbanVogue.admin.addProduct.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    // Explore section with pagination default page = 0, size = 3
    @GetMapping("/getProducts")
    public List<CatalogProductDTO> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return catalogService.getProducts(page, size);
    }

    // Search products by category with pagination no caching here
    @GetMapping("/search")
    public List<CatalogProductDTO> searchByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return catalogService.searchByCategory(category, page, size);
    }

    // Product detail
    @GetMapping("/getProducts/{id}")
    public Product getProductById(@PathVariable Long id) {
        return catalogService.getProductById(id);
    }
}
