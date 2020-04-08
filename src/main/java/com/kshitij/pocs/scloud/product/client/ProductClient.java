package com.kshitij.pocs.scloud.product.client;

import com.kshitij.pocs.scloud.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/client/product/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        log.info("Entered the get Product Service");
        return restTemplate.getForEntity("http://PRODUCT-SERVICE"+"/product/1",Product.class);
    }
}
