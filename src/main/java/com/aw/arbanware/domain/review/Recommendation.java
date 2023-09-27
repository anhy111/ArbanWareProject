package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(RecommendationKey.class)
public class Recommendation {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;  //후기번호

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  //회원번호
}
