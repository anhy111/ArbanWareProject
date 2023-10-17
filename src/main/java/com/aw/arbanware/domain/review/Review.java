package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "REVIEW_SEQUENCE")
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
