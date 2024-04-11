package com.siramiks.PaymentService.model;

import com.siramiks.PaymentService.feign.OrderService.OrderResponse;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PaymentResponse {
  private UUID paymentId;
  private String paymentStatus;

}
