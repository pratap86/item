package com.pratap.item.proxy;

import com.pratap.item.model.response.CouponResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "coupon-service", url = "http://localhost:9092/coupon-service/coupons/")
public interface CouponProxy {

    @GetMapping("/{code}")
    CouponResponseModel getCouponByCode(@PathVariable("code") String code);

}
