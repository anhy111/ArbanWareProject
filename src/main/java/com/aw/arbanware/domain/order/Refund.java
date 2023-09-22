package com.aw.arbanware.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Refund {

    @Id
    @GeneratedValue
    @Column(name = "REFUND_ID")
    private Long id; //환불 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order; //주문번호

    private String method; //환불수단
    private String status; //상태
    private int amount; //금액
    private String reason; //사유
    private String depositor; //예금주
    private String accountNumber; //계좌번호
    private String bank; //은행


}
