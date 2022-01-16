package com.saggu.eshop.controller;

import com.saggu.eshop.dao.ProductDao;
import com.saggu.eshop.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1/")
public class ProductController {

    private final ProductDao productDao;

    ThreadLocalRandom random = ThreadLocalRandom.current();

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Operation(summary = "Get products", description = "Get a list of Products", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found",
                    content = @Content)})
    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    public List<ProductDto> products() throws InterruptedException {
        return productDao.getProducts();
    }

    @GetMapping(value = "/products/{productId}", produces = APPLICATION_JSON_VALUE)
    public ProductDto products(@PathVariable String productId) throws InterruptedException {
        return productDao.getProduct(productId).get();
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productDao.addProduct(productDto));
    }

    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId,
                                                    @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productDao.updateProduct(productId, productDto));
    }
}