package com.kshitij.pocs.scloud.product.message;

import com.kshitij.pocs.scloud.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductUpdateMessage implements Serializable {
    Product product;
    Boolean isDeleted=Boolean.FALSE;
}
