package com.pratap.item.restclients;

import com.pratap.item.model.response.CouponResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client talks to Naming Server and pick up the one of the instance of "coupon-service"
 * from registered instances ie. do the load balancing between them
 */
@FeignClient(name = "coupon-service", url = "http://localhost:9092/coupon-service")
public interface CouponServiceProxy {

    @GetMapping("coupons/{code}")
    public ResponseEntity<CouponResponseModel> getCouponByCode(@PathVariable("code") String code);

}
