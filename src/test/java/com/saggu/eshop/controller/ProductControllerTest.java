package com.saggu.eshop.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.saggu.eshop.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Test
    void givenProducts_GetProductsEndpoint_ShouldReturnProductsList() {
        String baseUrl = "http://localhost:" + port + "/v1/products";
        ResponseEntity<ProductDto[]> response =
                restTemplate.getForEntity(baseUrl, ProductDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    void givenANewProduct_PostProductsEndpoint_ShouldAddANewProduct() {
        String baseUrl = "http://localhost:" + port + "/v1/products";
        ProductDto productSamsung =
                new ProductDto("Sony 4K TV 75", 3049.99, "Sony LED 4k Smart TV");
        ResponseEntity<ProductDto> response =
                restTemplate.postForEntity(baseUrl, productSamsung, ProductDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ProductDto newDto = response.getBody();
        assertThat(newDto).isNotNull();
        assertEquals(productSamsung.name(), newDto.name(), "Product Names Should be Same");
    }
}
