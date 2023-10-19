package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.payment.BankType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "refund_seq")
    @SequenceGenerator(name = "refund_seq",sequenceName = "REFUND_SEQUENCE",allocationSize = 1)
    @Column(name = "REFUND_ID")
    private Long id; //환불 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order; //주문번호


    @Enumerated(EnumType.STRING)
    private RefundMethod method; //환불수단

    @Enumerated(EnumType.STRING)
    private RefundStatus status; //상태

    private int amount; //금액
    private String reason; //사유
    private String depositor; //예금주
    private String accountNumber; //계좌번호

    @Enumerated(EnumType.STRING)
    private BankType bank; //은행


}
