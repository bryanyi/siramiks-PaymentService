package com.siramiks.PaymentService.service;

import com.siramiks.PaymentService.dto.StripeChargeDto;
import com.siramiks.PaymentService.dto.StripeTokenDto;
import com.siramiks.PaymentService.model.CardInfo;
import com.siramiks.PaymentService.entity.Payment;
import com.siramiks.PaymentService.feign.OrderService.OrderService;
import com.siramiks.PaymentService.model.*;
import com.siramiks.PaymentService.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class PaymentService {

  @Value("${STRIPE_SECRET_KEY}")
  private String stripeApiKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripeApiKey;
  }

  @Autowired
  private PaymentRepository paymentRepository;
  @Autowired
  private OrderService orderService;

  // TODO - change repsonse type to something more useful for the client
  public PaymentResponse completeTransaction(PaymentRequest paymentRequest) {
    // Process payment through stripe
    log.info("Init stripe payment processing...");
    PaymentIntent paymentIntent = this.processStripePayment(paymentRequest.getStripePaymentRequest());
    assert paymentIntent != null;
    ChargeCollection chargeCollection = paymentIntent.getCharges();
    String stripePaymentIntentId = paymentIntent.getId();
    log.info("Stripe payment was successfull!");

    // Create object to store in DB and return for client.
    NewOrderDetails orderDetails = paymentRequest.getNewOrderDetails();
    StripePaymentRequest stripePaymentRequest = paymentRequest.getStripePaymentRequest();
    CardInfo cardInfo = paymentRequest.getCardInfo();

    String stripePaymentStatus = "SUCCESS";
    Payment payment = Payment.builder()
            .orderId(paymentRequest.getOrderId())
            .cardId(cardInfo.getCardId())
            .totalPayment(orderDetails.getOrderPrice())
            .paymentMethod(orderDetails.getPaymentMethod())
            .stripePaymentIntentId(stripePaymentIntentId)
            .paymentStatus(stripePaymentStatus)
            .build();

    log.info("COMPLETE TRANSACTION METHOD - final payment data: {}", payment);

    payment = paymentRepository.save(payment);
    log.info("TRANSACTION SUCCESSFULLY SAVED", payment);

    // Create PaymenrResponse for client
    // UUID uuidPlaceholder = UUID.randomUUID();
    PaymentResponse paymentResponse = PaymentResponse.builder()
            .paymentStatus("SUCCESS")
            .paymentId(payment.getPaymentId())
            .chargeCollection(chargeCollection)
            .build();

    return paymentResponse;
  }

  public PaymentIntent createPaymentIntent(StripePaymentRequest stripePaymentRequest) {
    log.info("PRODUCT SERVICE: fetching payment intent");
    try {
      PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
              .setAmount((long) (stripePaymentRequest.getAmount() * 100)) // Convert to cents
              .setCurrency("usd")
              .setPaymentMethod(stripePaymentRequest.getPaymentMethodId())
              .setConfirm(false)
              .build();

      PaymentIntent paymentIntent = PaymentIntent.create(params);
      return paymentIntent;
    } catch (StripeException e) {
      throw new RuntimeException("Error creating Stripe payment intent", e);
    }
  }

  public void confirmPaymentIntent(String paymentIntentId) {
    log.info("PRODUCT SERVICE: confirming payment intent");
    try {

      PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
      paymentIntent.confirm();
    } catch (StripeException e) {
      throw new RuntimeException("Error confirming Stripe payment intent", e);
    }
  }

  public PaymentIntent retrievePaymentIntent(String paymentIntentId) {
    log.info("PRODUCT SERVICE: retieving payment intent");
    try {
      return PaymentIntent.retrieve(paymentIntentId);
    } catch (StripeException e) {
      throw new RuntimeException("Error retrieving Stripe payment intent", e);
    }
  }

  private PaymentIntent processStripePayment(StripePaymentRequest paymentRequest) {
    try {
      log.info("fetching client secret...");
      PaymentIntent paymentIntent = this.createPaymentIntent(paymentRequest);

      log.info("confiirming payment intent...");
      this.confirmPaymentIntent(paymentIntent.getId());
      log.info("payment intent confirmed!!");

      PaymentIntent retrievedPaymentIntent = this.retrievePaymentIntent(paymentIntent.getId());

      return retrievedPaymentIntent;
    } catch (RuntimeException e) {
      return null;
    }
  }


  public ChargeCollection testStripeTransaction(StripePaymentRequest stripePaymentRequest) {
    PaymentIntent paymentIntent = this.processStripePayment(stripePaymentRequest);
    assert paymentIntent != null;
    ChargeCollection chargeCollection = paymentIntent.getCharges();
    return chargeCollection;
  }


  // Integrating Stripe this way didn't work
  public StripeTokenDto createCardToken(StripeTokenDto model) {
    try {
      Map<String, Object> card = new HashMap<>();
      card.put("number", model.getCardNumber());
      card.put("exp_month", Integer.parseInt(model.getExpMonth()));
      card.put("exp_year", Integer.parseInt(model.getExpYear()));
      card.put("cvc", model.getCvc());

      Map<String, Object> params = new HashMap<>();
      params.put("card", card);
      Token token = Token.create(params);

      if (token != null && token.getId() != null) {
        model.setSuccess(true);
        model.setToken(token.getId());
      }

      return model;

    } catch (StripeException e) {
      log.error("Stripe service error: {}", e);
      throw new RuntimeException(e.getMessage());
    }
  }
}
