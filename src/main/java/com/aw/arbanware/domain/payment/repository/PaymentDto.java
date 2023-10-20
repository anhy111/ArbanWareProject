package com.aw.arbanware.domain.payment.repository;

import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.payment.BankType;
import com.aw.arbanware.domain.payment.PaymentMethod;
import com.aw.arbanware.domain.payment.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {

    private Long id;        // 결제 번호

    private Order order;    // 주문번호

    private PaymentMethod method;   // 결제수단

    private PaymentStatus status;   // 결제상태
    private LocalDateTime paymentDate;  // 결제일

    private int totalAmount;        // 실 결제금액
    private int additionalAmount;   // 추가 입금액
    private int depositAmount;      // 입금액
    private String depositor;   // 입금자
    private BankType bank;      // 입금은행
}
