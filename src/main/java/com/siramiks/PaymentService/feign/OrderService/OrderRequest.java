package com.siramiks.PaymentService.feign.OrderService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

  private List<UUID> productIds;
  private long orderPrice;
  private long orderQuantity;
  private String paymentMethod;
  private UUID orderId;
}
