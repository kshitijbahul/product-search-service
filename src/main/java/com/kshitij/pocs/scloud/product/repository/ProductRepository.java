package com.kshitij.pocs.scloud.product.repository;

import com.kshitij.pocs.scloud.product.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends ElasticsearchCrudRepository<Product,String> {
    List<Product> getProductByCategory(Long categoryId);
}
