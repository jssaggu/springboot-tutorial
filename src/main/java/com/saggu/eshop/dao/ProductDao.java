package com.saggu.eshop.dao;

import com.saggu.eshop.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    static int id=100;

    private final List<ProductDto> productList = new ArrayList<>();

    public ProductDao() {
        productList.add(ProductDto.builder().productId(createAndGetId()).name("Sony 4K TV 65").price(2049.99).description("Sony LED 4k Smart TV").build());
        productList.add(ProductDto.builder().productId(createAndGetId()).name("Sony 4K TV 55").price(1049.99).description("Sony LED 4k Smart TV").build());
        productList.add(ProductDto.builder().productId(createAndGetId()).name("Sony 4K TV 75").price(3049.99).description("Sony LED 4k Smart TV").build());
    }

    private String createAndGetId() {
        return "P" + id++;
    }

    public List<ProductDto> getProducts() {
        return productList;
    }

    public ProductDto addProduct(ProductDto product) {
        product.setProductId(createAndGetId());
        productList.add(product);
        return product;
    }
}
