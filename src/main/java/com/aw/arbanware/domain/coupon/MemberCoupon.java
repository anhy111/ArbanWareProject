package com.aw.arbanware.domain.coupon;

import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class MemberCoupon {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "member_coupon_seq")
    @SequenceGenerator(name = "member_coupon_seq",sequenceName = "MEMBER_COUPON_SEQUENCE",allocationSize = 1)
    @Column(name = "MEMBER_COUPON_ID")
    private Long id; //회원 쿠폰 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //회원 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon; // 쿠폰 번호

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime issueDate; //발급일

    private LocalDateTime expirationDate; //만료일

    @Enumerated(EnumType.STRING)
    private CouponUsageYn usageStatus; //사용여부
    
}
