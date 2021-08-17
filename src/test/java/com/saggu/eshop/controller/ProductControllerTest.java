package com.saggu.eshop.controller;

import com.saggu.eshop.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenProducts_GetProducts_ShouldReturnProductsList() {
        String BASE_URL = "http://localhost:" + port + "/products";
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(BASE_URL, ProductDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void givenProducts_PostProducts_ShouldAddANewProduct() {
        String BASE_URL = "http://localhost:" + port + "/products";
        ProductDto productSamsung = ProductDto.builder()
                .name("Samsung TV").price(2000.00).description("Samsung Smart 4K")
                .build();
        ResponseEntity<ProductDto> response = restTemplate
                .postForEntity(BASE_URL, productSamsung, ProductDto.class);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
    }
}