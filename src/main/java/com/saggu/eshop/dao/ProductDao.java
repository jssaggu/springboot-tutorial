package com.saggu.eshop.dao;

import com.saggu.eshop.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@Slf4j
public class ProductDao {

    static int id = 100;

    private final String prefix;

    private final List<ProductDto> productList = new ArrayList<>();

    public ProductDao(@Value("${products.prefix:}") String prefix) {
        log.debug("Hello Constructor");
        this.prefix = prefix;
        productList.add(ProductDto.builder().productId(createAndGetId()).name(this.prefix + "Sony 4K TV 65").price(2049.99).description("Sony LED 4k Smart TV").build());
        productList.add(ProductDto.builder().productId(createAndGetId()).name(this.prefix + "Sony 4K TV 55").price(1049.99).description("Sony LED 4k Smart TV").build());
        productList.add(ProductDto.builder().productId(createAndGetId()).name(this.prefix + "Sony 4K TV 75").price(3049.99).description("Sony LED 4k Smart TV").build());
        log.warn("This is a warning that I have added 3 products only...");
    }

    private String createAndGetId() {
        return "P" + id++;
    }

    @Cacheable("products")
    public List<ProductDto> getProducts() {
        sleep(1);
        System.out.println("Calling service to get Products data...");
        return new ArrayList<>(productList);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDto addProduct(ProductDto product) {
        product.setProductId(createAndGetId());
        product.setName(prefix + product.getName());
        productList.add(product);
        return product;
    }

    private void sleep(int seconds) {
        try {
            SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
