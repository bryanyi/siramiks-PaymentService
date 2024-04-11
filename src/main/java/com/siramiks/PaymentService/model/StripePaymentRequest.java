package com.siramiks.PaymentService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StripePaymentRequest {
  private double amount;
  private String paymentMethodId;
}
