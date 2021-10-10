package com.saggu.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ProductDto implements Serializable {
    private String productId;
    private String name;
    private double price;
    private String description;
}
