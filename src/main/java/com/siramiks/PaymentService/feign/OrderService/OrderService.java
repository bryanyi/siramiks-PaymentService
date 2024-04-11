package com.siramiks.PaymentService.feign.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE/api/v1/order")
public interface OrderService {

  @PostMapping("/createOrder")
  OrderResponse createOrder(@RequestBody OrderRequest orderRequest);

}
