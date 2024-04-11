package com.siramiks.PaymentService.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class StripeChargeDto {
  private String stripeToken;
  private String username;
  private Double amount;
  private String status;
  private String message;
  private String chargeId;
  private Map<String, Object> additionalInfo = new HashMap<>();
}
