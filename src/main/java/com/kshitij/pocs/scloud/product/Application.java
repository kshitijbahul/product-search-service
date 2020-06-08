package com.kshitij.pocs.scloud.product;

import com.google.gson.Gson;
import com.kshitij.pocs.scloud.product.listeners.ProductListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class Application {
    @Value("${rabbit.product.topic}")
    String queueName;
    @Value("${rabbit.product.topicExchange}")
    String topicExchange;
    @Value("${rabbit.product.routingKey}")
    String routingKey;
    /*
    No need to create the Queue and Bind it based on the routing key if teh infra already exists
    On the other hand it will be nice to keep this code to create the Bindings if they Don't exists on startup
     */
    /*@Bean
    Jackson2JsonMessageConverter getJackson2JsonMessageConverter(){return new Jackson2JsonMessageConverter();}*/

    /*@Bean
    Queue createQueue(){
        return new Queue(queueName,Boolean.FALSE);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchange);
    }
    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange){
        //Any message sent to the exchange with routing key(beginning with product.update) are sent to the queue
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }*/
    @Bean
    /*MessageListenerAdapter listenerAdapter(ProductListener listener, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        return new MessageListenerAdapter(listener, jackson2JsonMessageConverter);
    }*/
    MessageListenerAdapter listenerAdapter(ProductListener listener) {
        return new MessageListenerAdapter(listener, "listen");
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        /*
        This I feel is kind of crucial to prevent declaration of the Queue in the handler class
         */
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }



    public static void main(String[] args) {
            SpringApplication.run(Application.class,args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    Gson setUpGson(){
        return new Gson();
    }
}
