package com.aw.arbanware.domain.payment;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Payment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq",sequenceName = "PAYMENT_SEQUENCE",allocationSize = 1)
    @Column(name = "PAYMENT_ID")
    private Long id;        // 결제 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;    // 주문번호

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;   // 결제수단

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;   // 결제상태
    private LocalDateTime paymentDate;  // 결제일

    private int totalAmount;        // 실 결제금액
    private int additionalAmount;   // 추가 입금액
    private int depositAmount;      // 입금액
    private String depositor;   // 입금자

    @Enumerated(EnumType.STRING)
    private BankType bank;      // 입금은행
}
