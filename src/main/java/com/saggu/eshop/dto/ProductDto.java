package com.saggu.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String name;
    private double price;
    private String description;
}
