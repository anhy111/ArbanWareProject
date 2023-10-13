package com.aw.arbanware.domain.cart.entity;

import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class CartKey implements Serializable {

    private Long productInfoId;    // 상품 정보번호
    private Long memberId;  // 회원번호

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CartKey cartKey = (CartKey) o;
        return Objects.equals(productInfoId, cartKey.productInfoId) && Objects.equals(memberId, cartKey.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInfoId, memberId);
    }
}
