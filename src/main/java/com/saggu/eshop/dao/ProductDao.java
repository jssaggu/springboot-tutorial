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
        productList.add(
        ProductDto.builder().productId("P" + id++).name("Sony 4K TV 65").price(2049.99).description("Sony LED 4k Smart TV").build());
        productList.add(ProductDto.builder().productId("P" + id++).name("Sony 4K TV 55").price(1049.99).description("Sony LED 4k Smart TV").build());
    }

    public List<ProductDto> getProducts() {
        return productList;
    }

    public ProductDto addProduct(ProductDto product) {
        product.setProductId("P" + (id++));
        productList.add(product);
        return product;
    }
}
