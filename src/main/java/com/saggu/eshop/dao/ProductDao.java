package com.saggu.eshop.dao;

import com.saggu.eshop.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@Slf4j
public class ProductDao {
    static int id = 100;
    private final String prefix;
    private final Map<String, ProductDto> productList = new HashMap<>();

    public ProductDao(@Value("${products.prefix:}") String prefix) {
        this.prefix = prefix;
        addProducts();
    }

    public void addProducts() {
        log.debug("Hello Constructor");
        String id = createAndGetId();
        productList.put(id, ProductDto.builder().productId(id).name(this.prefix + "Sony 4K TV 65").price(2049.99).description("Sony LED 4k Smart TV").build());
        id = createAndGetId();
        productList.put(id, ProductDto.builder().productId(id).name(this.prefix + "Sony 4K TV 55").price(1049.99).description("Sony LED 4k Smart TV").build());
        id = createAndGetId();
        productList.put(id, ProductDto.builder().productId(id).name(this.prefix + "Sony 4K TV 75").price(3049.99).description("Sony LED 4k Smart TV").build());
        log.warn("This is a warning that I have added 3 products only...");
    }

    private String createAndGetId() {
        return "P" + id++;
    }

    @Cacheable("products")
    public List<ProductDto> getProducts() throws InterruptedException {
        log.info("Calling service to get Products data...");
        SECONDS.sleep(5);
        log.debug("This is debug Calling service to get Products data...");
        return new ArrayList<>(productList.values());
    }

    @CachePut(value = "products")
    public ProductDto addProduct(ProductDto product) {
        String id = createAndGetId();
        product.setProductId(id);
        product.setName(prefix + product.getName());
        productList.put(id, product);
        return product;
    }

    @CachePut(value = "products", key = "#productId")
    public ProductDto updateProduct(String productId, ProductDto product) {
        product.setProductId(productId);
        productList.put(productId, product);
        return product;
    }

    @Cacheable(value = "products", key = "#productId")
    public Optional<ProductDto> getProduct(String productId) {
        log.info("Finding product: " + productId);
        sleep(2);
        return Optional.ofNullable(productList.get(productId));
    }

    private void sleep(int seconds) {
        try {
            SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
