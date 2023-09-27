package com.aw.arbanware.domain.mileage;

import com.aw.arbanware.domain.order.Order;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MileageHistory {

    @Id
    @GeneratedValue
    @Column(name = "MILEAGE_HISTORY_ID")
    private Long id; //마일리지 내역

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //회원번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private String status; //상태
    private int accumulationMileage; // 적립금
    private LocalDateTime usageTime; // 사용일시

}