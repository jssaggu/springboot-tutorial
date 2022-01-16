package com.saggu.eshop.dao;

import com.saggu.eshop.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@Slf4j
public class OrderDao {

    private static final Map<String, ProductDto> orders = new HashMap<>();
    private static AtomicInteger ORDER_ID = new AtomicInteger(100);
    private final ProductDao productDao;

    public OrderDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Map<String, ProductDto> getOrders() throws InterruptedException {
        log.info("Calling service to get Orders data...");
//        SECONDS.sleep(1);
        return orders;
    }

    @Cacheable(value = "orders", key = "#orderId")
    public ProductDto getOrder(String orderId) throws InterruptedException {
        log.info("Calling service to get Orders data...");
        SECONDS.sleep(5);
        return orders.get(orderId);
    }

    @CachePut(value = "orders")
    public String addOrder(String productId) {
        String orderId = "O" + ORDER_ID.getAndIncrement();
        Optional<ProductDto> product = productDao.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get());
            return orderId;
        } else {
            throw new OpenApiResourceNotFoundException("Invalid Product ID: " + productId);
        }
    }

    @CachePut(value = "orders", key = "#orderId")
    public ProductDto updateOrder(String orderId, String productId) {
        Optional<ProductDto> product = productDao.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get());
            return product.get();
        } else {
            throw new OpenApiResourceNotFoundException("Invalid Product ID: " + productId);
        }
    }
}
