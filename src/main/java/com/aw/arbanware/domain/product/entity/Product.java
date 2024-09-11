package com.aw.arbanware.domain.product.entity;

import com.aw.arbanware.domain.category.entity.Category;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "product_seq",sequenceName = "PRODUCT_SEQUENCE",allocationSize = 1)
    @Column(name = "PRODUCT_ID")
    private Long id;    // 상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;  // 카테고리번호

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages = new ArrayList<>();   // 상품이미지들

    @OneToMany(mappedBy = "product")
    private List<ProductInfo> productInfos = new ArrayList<>();

    private String name;    //상품명

    private String content;    //내용
    private int cost;           //원가
    private int price;          //판매가
    private String thumbnail;   //대표이미지

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn = DeleteYn.N;  //삭제여부

    public void updateProductExceptThumbnail(Product other) {
        this.category = other.category;
        this.name = other.name;
        this.content = other.content;
        this.cost = other.cost;
        this.price = other.price;
        this.deleteYn = other.deleteYn;
    }

}
