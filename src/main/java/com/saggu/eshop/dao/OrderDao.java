/* (C)2023 */
package com.saggu.eshop.dao;

import com.saggu.eshop.dto.ProductDto;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OrderDao {

    private static final Map<String, String> orders = new HashMap<>();
    private static final AtomicInteger ORDER_ID = new AtomicInteger(100);
    private final ProductDao productDao;

    public OrderDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Map<String, ProductDto> getOrders() {
        log.info("Calling service to get Orders data...");
        Map<String, ProductDto> ordersWithDto = new HashMap<>();
        for (Map.Entry<String, String> entry : orders.entrySet()) {
            productDao
                    .getProduct(entry.getValue())
                    .ifPresent(p -> ordersWithDto.put(entry.getKey(), p));
        }
        return ordersWithDto;
    }

    public ProductDto getOrder(String orderId) {
        log.info("Calling service to get Orders data...");
        return productDao
                .getProduct(orders.get(orderId))
                .orElseThrow(() -> new NoSuchElementException("OrderId not found: " + orderId));
    }

    public String addOrder(String productId) {
        String orderId = "O" + ORDER_ID.getAndIncrement();
        Optional<ProductDto> product = productDao.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get().productId());
            return orderId;
        } else {
            throw new NoSuchElementException("Invalid Product ID: " + productId);
        }
    }

    public ProductDto updateOrder(String orderId, String productId) {
        Optional<ProductDto> product = productDao.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get().productId());
            return product.get();
        } else {
            throw new OpenApiResourceNotFoundException("Invalid Product ID: " + productId);
        }
    }
}
