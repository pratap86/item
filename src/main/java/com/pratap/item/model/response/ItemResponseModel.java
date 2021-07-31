package com.pratap.item.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemResponseModel {

    private String itemId;
    private String name;
    private String description;
    private BigDecimal price;
}
