package com.aw.arbanware.domain.review.entity;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "review_seq")
//    @SequenceGenerator(name = "review_seq",sequenceName = "REVIEW_SEQUENCE",allocationSize = 1)
    @Column(name = "REVIEW_ID")
    private Long id; // 후기번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct;    //상품 정보 번호

    private int rating; //별점
    private String content; //내용
    private Long attachFileId;  //첨부파일번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  //회원번호

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn = DeleteYn.N;  //삭제 여부
}
