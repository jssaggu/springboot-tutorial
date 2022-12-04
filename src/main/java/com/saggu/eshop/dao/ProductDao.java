package com.saggu.eshop.dao;

import com.hazelcast.cache.HazelcastCacheManager;
import com.hazelcast.cache.ICache;
import com.hazelcast.cache.impl.HazelcastInstanceCacheManager;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICacheManager;
import com.saggu.eshop.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.saggu.eshop.utils.AppUtils.printMemory;
import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
@Slf4j
public class ProductDao {
    static int id = 100;
    private final String prefix;
    private final HazelcastInstance hazelcastInstance;

    private final Map<String, ProductDto> productList;
//    private final IMap<String, ProductDto> productListHC;

    public ProductDao(@Value("${products.prefix:}") String prefix, HazelcastInstance hazelcastInstance) {
        this.prefix = prefix;
        this.hazelcastInstance = hazelcastInstance;
//        productList = hazelcastInstance.getMap("products");
        productList = new HashMap<>();
//        productListHC = hazelcastInstance.getMap("products-hc");
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

    public static String createAndGetId() {
        return "P" + id++;
    }

    @Cacheable("products")
    public List<ProductDto> getProducts() throws InterruptedException {
        log.info("Calling service to get Products data...");
        SECONDS.sleep(5);
        log.debug("This is debug Calling service to get Products data...");
        return new ArrayList<>(productList.values());
    }

    //@CachePut(value = "products")
    @Caching(
            put = {
                    @CachePut(cacheNames = "products"),
                    @CachePut(cacheNames = "products", key = "#product.productId")
            }
    )
    public ProductDto addProduct(ProductDto product) {
        //printMemory(product.getProductId());
        product.setName(prefix + product.getName());
        product.setDescription(addChars(500_000));
        productList.put(product.getProductId(), product);

        HazelcastInstanceCacheManager cacheManager = (HazelcastInstanceCacheManager)hazelcastInstance.getCacheManager();

//        System.out.println(cacheManager.getCacheNames());

        printMemory(product.getProductId() + " - " + productList.size() );
        return product;
    }

    private String addChars(int totalCharsToAdd) {
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < totalCharsToAdd; i++) {
            sb.append("A");
        }
        return sb.toString();
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
