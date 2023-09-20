package com.aw.arbanware.domain.cart;

import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class CartKey implements Serializable {

    private ProductInfo productInfo;
    private Member member;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CartKey cartKey = (CartKey) o;
        return Objects.equals(productInfo, cartKey.productInfo) && Objects.equals(member, cartKey.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInfo, member);
    }
}
