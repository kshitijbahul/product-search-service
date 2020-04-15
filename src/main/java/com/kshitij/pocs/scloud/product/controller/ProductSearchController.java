package com.kshitij.pocs.scloud.product.controller;

import com.kshitij.pocs.scloud.product.exceptions.ProductNotFoundException;
import com.kshitij.pocs.scloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProductSearchController {


    private ProductService productService;

    @Autowired
    public ProductSearchController(ProductService productService){
        this.productService=productService;
    }


    @GetMapping("/product/{id}")
    ResponseEntity getProduct(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }
    @GetMapping("/products")
    ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(this.productService.getAllProducts());
    }
    @GetMapping("/products/category")
    ResponseEntity<?> getProductByCategoryId(@RequestParam String categoryId){
        log.info("Got the Request Parma as "+categoryId);
        return ResponseEntity.ok(this.productService.getProductByCategory(Long.valueOf(categoryId)));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<?> productNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
    }
}
