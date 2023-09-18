package com.aw.arbanware.domain.product;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class ProductImageKey implements Serializable {

    private Product product;
    private int sequence;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductImageKey that = (ProductImageKey) o;
        return sequence == that.sequence && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, sequence);
    }
}
