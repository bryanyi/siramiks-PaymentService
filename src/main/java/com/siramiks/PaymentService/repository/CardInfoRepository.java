package com.siramiks.PaymentService.repository;

import com.siramiks.PaymentService.entity.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardInfoRepository extends JpaRepository<CardInfo, UUID> {
  CardInfo findByCardNumber(String cardNumber);
}
