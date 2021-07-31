package com.pratap.item.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {

    private Long id;
    private String itemId;
    private String name;
    private String description;
    private BigDecimal price;
    private String couponCode;
}
