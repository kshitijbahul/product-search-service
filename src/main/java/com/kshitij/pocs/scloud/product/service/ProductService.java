package com.kshitij.pocs.scloud.product.service;

import com.kshitij.pocs.scloud.product.entity.Product;
import com.kshitij.pocs.scloud.product.exceptions.ProductNotFoundException;
import com.kshitij.pocs.scloud.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private ProductRepository productRepository;


    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public Product getProductById(String id) {
        return this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
    public Iterable<Product> getAllProducts(){
        return this.productRepository.findAll();
    }
    public void createProduct(Product product){
        this.productRepository.save(product);
    }
    public List<Product> getProductByCategory(Long categoryId){
        return this.productRepository.getProductByCategory(categoryId);
    }

}
