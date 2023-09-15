package com.aw.arbanware.domain.delivery;

import com.aw.arbanware.domain.common.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String recipient;

    @Embedded
    private Address address;
    private String phoneNumber;
    private String requirements;

    public Delivery() {
    }

    public Delivery(final Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id) && Objects.equals(recipient, delivery.recipient) && Objects.equals(address, delivery.address) && Objects.equals(phoneNumber, delivery.phoneNumber) && Objects.equals(requirements, delivery.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipient, address, phoneNumber, requirements);
    }
}
