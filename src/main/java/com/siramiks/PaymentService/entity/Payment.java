package com.siramiks.PaymentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  // auto-generated via @PrePersist created below
  @Column(name = "payment_id")
  private UUID paymentId;

  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "card_info_id")
  private UUID cardId;

  @CreationTimestamp
  @Column(name = "payment_created_at")
  private LocalDateTime createdAt;

  @Column(name = "payment_total")
  private double totalPayment;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "stripe_payment_intent_id")
  private String stripePaymentIntentId;

  @Column(name = "payment_status")
  private String paymentStatus;

  /* will be automatically invoked by the JPA provider before the entity is persisted */
  @PrePersist
  protected void onCreate() {
    if (this.paymentId == null) {
      // Generate a UUID if productId is not set
      this.paymentId = UUID.randomUUID();
    }
  }

}
