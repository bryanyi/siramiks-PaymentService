package com.siramiks.PaymentService.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PaymentRequest {
  private UUID orderId;
  private StripePaymentRequest stripePaymentRequest;
  private NewOrderDetails newOrderDetails;
  private CardInfo cardInfo;
}
