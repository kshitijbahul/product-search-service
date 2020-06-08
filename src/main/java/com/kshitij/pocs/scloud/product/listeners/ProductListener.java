package com.kshitij.pocs.scloud.product.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kshitij.pocs.scloud.product.entity.Product;
import com.kshitij.pocs.scloud.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

//import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductListener {

    final String QUEUE_NAME="product-queue";

    private ProductRepository productRepository;

    @Autowired
    private Gson gson;

    @Autowired
    ProductListener(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    /*
    After a Zillion Iterations
    Accepting the message in Spring-messaging Format to leverage the headers
    To not create the Entities of Events each side, Using Gson to pass through data
    Extraction only the info needed
    On the other Hand if creating Entities in each project is a conclusion I reach
    I can replace Message<> with the Entity directly to let Spring deserialize
    Don't need the @RabbitListener(queues = QUEUE_NAME) as long as the Rabbit container is listening to this queue
     */
    //@RabbitListener(queues = QUEUE_NAME)
    public void listen(Message<String> inputMessage){
        log.info("Got a message",inputMessage.toString());
        log.info(inputMessage.toString());

        JsonObject jsonObject = gson.fromJson(inputMessage.getPayload(), JsonObject.class);
        Product product= gson.fromJson(jsonObject.getAsJsonObject("product").toString(),Product.class);
        if(jsonObject.get("isDeleted").getAsBoolean()==Boolean.TRUE){
            log.info("Going to Delete the message");
            this.productRepository.delete(product);
        }else{
            log.info("Saving or updating the Message");
            this.productRepository.save(product);
        }
        //put the message in elastic
        //while getting that is the fun part
    }
    /*
    TO create message based on JackSon2JsonConverter
     */
    /*@RabbitListener(queues = QUEUE_NAME)
    public void handleMessage(Object message){
        log.info(" in handleMessage for {}",message);
    }*/
}
