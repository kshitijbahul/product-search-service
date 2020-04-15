package com.kshitij.pocs.scloud.product.listeners;

import com.kshitij.pocs.scloud.product.message.ProductUpdateMessage;
import com.kshitij.pocs.scloud.product.repository.ProductRepository;
import com.kshitij.pocs.scloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductListener {

    final String QUEUE_NAME="product-queue";

    private ProductRepository productRepository;
    @Autowired
    ProductListener(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void listen(ProductUpdateMessage inputMessage){
        log.info("Got a message",inputMessage.toString());
        log.info(inputMessage.toString());
        if(inputMessage.getIsDeleted()==Boolean.TRUE){
            log.info("Going to Delete the message");
            this.productRepository.delete(inputMessage.getProduct());
        }else{
            log.info("Saving or updating the Message");
            this.productRepository.save(inputMessage.getProduct());
        }
        //put the message in elastic
        //while getting that is the fun part
    }
}
