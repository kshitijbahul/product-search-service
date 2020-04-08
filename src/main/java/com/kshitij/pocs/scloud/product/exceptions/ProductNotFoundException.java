package com.kshitij.pocs.scloud.product.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
        super("Product Not Found");
    }


}
