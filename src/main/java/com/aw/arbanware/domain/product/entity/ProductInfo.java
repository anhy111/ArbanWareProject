package com.aw.arbanware.domain.product.entity;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
public class ProductInfo extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_INFO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Size size;

    private int inventory;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductInfo that = (ProductInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", color=" + color +
                ", size=" + size +
                ", inventory=" + inventory +
                '}';
    }
}
