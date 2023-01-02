/* (C)2023 */
package com.saggu.eshop.dao;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.saggu.eshop.dto.ProductDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

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
        productList.put(
                id,
                new ProductDto(id, this.prefix + "Sony 4K TV 65", 2049.99, "Sony LED 4k Smart TV"));
        id = createAndGetId();
        productList.put(
                id,
                new ProductDto(id, this.prefix + "Sony 4K TV 55", 1049.99, "Sony LED 4k Smart TV"));
        id = createAndGetId();
        productList.put(
                id,
                new ProductDto(id, this.prefix + "Sony 4K TV 75", 3049.99, "Sony LED 4k Smart TV"));
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
        ProductDto productDto =
                new ProductDto(id, prefix + product.name(), product.price(), product.description());
        productList.put(id, productDto);
        return productDto;
    }

    @CachePut(value = "products", key = "#productId")
    public ProductDto updateProduct(String productId, ProductDto product) {
        ProductDto productDto =
                new ProductDto(productId, product.name(), product.price(), product.description());
        productList.put(productId, productDto);
        return productDto;
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
