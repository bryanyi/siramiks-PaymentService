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
@Table(name = "CardInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "cardInfo_id")
  private UUID cardInfoId;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "exp_month")
  private String expMonth;

  @Column(name = "exp_year")
  private String expYear;

  @Column(name = "cvc")
  private String cvc;

  /* will be automatically invoked by the JPA provider before the entity is persisted */
  @PrePersist
  protected void onCreate() {
    if (this.cardInfoId == null) {
      // Generate a UUID if productId is not set
      this.cardInfoId = UUID.randomUUID();
    }
  }

}
