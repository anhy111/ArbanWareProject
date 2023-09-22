package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.common.BaseTimeEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.product.Product;
import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id; // 후기번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;    //상품 정보 번호

    private int rating; //별점
    private String content; //내용
    private Long attachFileId;  //첨부파일번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  //회원번호

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn;  //삭제 여부
}
