package com.siramiks.PaymentService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardInfo {
  private UUID cardId;
  private String cardNumber;
  private String expMonth;
  private String expYear;
  private String cvc;
}
