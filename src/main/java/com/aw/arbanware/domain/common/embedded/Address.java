package com.aw.arbanware.domain.common.embedded;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {
    private String city;    // 도시
    private String street;  // 도로명
    private String zipcode; // 우편번호

    public Address() {
    }

    public Address(final String city, final String street, final String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
