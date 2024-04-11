package com.siramiks.PaymentService.model;

import com.siramiks.PaymentService.dto.StripeChargeDto;
import com.siramiks.PaymentService.dto.StripeTokenDto;
import com.siramiks.PaymentService.entity.CardInfo;
import com.siramiks.PaymentService.feign.OrderService.OrderRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
  private StripeTokenDto stripeToken;
  private StripeChargeDto stripeCharge;
  private CardInfo cardInfo;
  private NewOrderDetails newOrderDetails;
}
