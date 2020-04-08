package com.kshitij.poc.scloud.product.integration;

import com.kshitij.pocs.scloud.product.Application;
import com.kshitij.pocs.scloud.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = Application.class)
@Slf4j
public class IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String url;

    private Long productId;


    @Before
    public void prepareTestBed(){
        log.info("Came to prepare test bed");
        productId=1L;
        url=String.format("http://localhost:%d",port);
    }

    @BeforeEach
    private void beforeEachTest() throws MalformedURLException {
        log.info("Before each test ");

    }
    @Test
    public void testGetProduct_ShouldReturnProduct(){
        String url1=String.format(url+"/product/%d",productId);
        URI uri=URI.create(url1);
        ResponseEntity<Product> responseEntity= testRestTemplate.getForEntity(url, Product.class);
        assertThat(responseEntity.getBody(),isA(Product.class));
    }

}
