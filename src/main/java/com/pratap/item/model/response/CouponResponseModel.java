package com.pratap.item.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CouponResponseModel {

    private String couponId;
    private String code;
    private BigDecimal discount;
    private String expDate;
}
