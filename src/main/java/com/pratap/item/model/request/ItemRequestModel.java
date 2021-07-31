package com.pratap.item.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ItemRequestModel {

    @NotBlank(message = "Item name is a required field")
    private String name;

    @NotBlank(message = "Item description is a required field")
    private String description;

    @Min(value = 1, message = "price can not be lower than 1")
    private BigDecimal price;

    @NotBlank(message = "Item couponCode is a required field")
    private String couponCode;
}
