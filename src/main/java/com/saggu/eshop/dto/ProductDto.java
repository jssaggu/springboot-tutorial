/* (C)2023 */
package com.saggu.eshop.dto;

public record ProductDto(String productId, String name, double price, String description) {

    public ProductDto(String name, double price, String description) {
        this(null, name, price, description);
    }
}
