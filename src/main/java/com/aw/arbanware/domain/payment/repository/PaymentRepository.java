package com.aw.arbanware.domain.payment.repository;

import com.aw.arbanware.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
