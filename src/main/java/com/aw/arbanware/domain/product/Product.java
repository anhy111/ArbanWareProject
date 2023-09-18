package com.aw.arbanware.domain.product;

import com.aw.arbanware.domain.category.Category;
import com.aw.arbanware.domain.common.BaseEntity;
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
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private String content;
    private int cost;
    private int price;
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages = new ArrayList<>();
}
