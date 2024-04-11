package com.siramiks.PaymentService.controller;

import com.siramiks.PaymentService.model.PaymentRequest;
import com.siramiks.PaymentService.model.PaymentResponse;
import com.siramiks.PaymentService.model.StripePaymentRequest;
import com.siramiks.PaymentService.model.TransactionRequest;
import com.siramiks.PaymentService.service.PaymentService;
import com.stripe.model.ChargeCollection;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "https://www.siramiks.bryan.com")
@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
@Log4j2
public class PaymentController {

  @Autowired
  private final PaymentService paymentService;

  @PostMapping("/completeTransaction")
  public ResponseEntity<PaymentResponse> completeTransaction(@RequestBody PaymentRequest paymentRequest) {
    return new ResponseEntity<>(paymentService.completeTransaction(paymentRequest), HttpStatus.OK);
  }

  @PostMapping("/testStripe")
  public ResponseEntity<ChargeCollection> testStripeTransaction(@RequestBody StripePaymentRequest stripePaymentRequest) {
    ChargeCollection charge = paymentService.testStripeTransaction(stripePaymentRequest);
    return new ResponseEntity<>(charge, HttpStatus.OK);
  }


//  @PostMapping("/card/token")
//  public StripeTokenDto createCardToken(@RequestBody StripeTokenDto model) {
//    return paymentService.createCardToken(model);
//  }

//  @PostMapping("/charge")
//  @ResponseBody
//  public StripeChargeDto charge(@RequestBody StripeChargeDto model) {
//    return paymentService.charge(model);
//  }

//  @PostMapping("/initTransaction")
//  public ResponseEntity<PaymentResponse> initTransaction(@RequestBody PaymentRequest paymentRequest) {
//    log.info("PAYMENT CONTROLLER - paymentRequest object: {}", paymentRequest);
//    PaymentResponse paymentResponse = paymentService.initTransaction(paymentRequest);
//    return new ResponseEntity<>(paymentResponse, HttpStatus.ACCEPTED);
//  }

//  @PostMapping("/initTransaction")
//  public ResponseEntity<PaymentIntent> initTransaction(@RequestBody StripePaymentRequest stripePaymentRequest) {
//    double amount = 49.99;
//    PaymentIntent paymentIntent = paymentService.completeTransaction(stripePaymentRequest, amount);
//    return new ResponseEntity<>(paymentIntent, HttpStatus.OK);
//  }
}
