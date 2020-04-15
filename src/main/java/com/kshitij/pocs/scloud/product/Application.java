package com.kshitij.pocs.scloud.product;

import com.kshitij.pocs.scloud.product.listeners.ProductListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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


    @Bean
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
    }
    @Bean
    MessageListenerAdapter listenerAdapter(ProductListener listener) {
        return new MessageListenerAdapter(listener, "listen");
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
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
}
