package com.aw.arbanware.domain.coupon;

import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MemberCoupon {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_COUPON_ID")
    private Long id; //회원 쿠폰 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //회원 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon; // 쿠폰 번호
    
    private LocalDateTime issueDate; //발급일
    private LocalDateTime expirationDate; //만료일
    private String usageStatus; //사용여부
    
}
