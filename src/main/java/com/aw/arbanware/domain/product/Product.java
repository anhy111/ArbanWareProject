package com.aw.arbanware.domain.product;

import com.aw.arbanware.domain.category.Category;
import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;    // 상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;  // 카테고리번호

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages = new ArrayList<>();   // 상품이미지들

    private String name;    //상품명

    private String content;    //내용
    private int cost;           //원가
    private int price;          //판매가
    private String thumbnail;   //대표이미지

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn;  //삭제여부

}
