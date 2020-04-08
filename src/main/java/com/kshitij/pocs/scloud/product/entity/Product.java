package com.kshitij.pocs.scloud.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product",type = "external_bulk")
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private Long category;
}
