package com.aw.arbanware.domain.delivery.entity;

import com.aw.arbanware.domain.common.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq",sequenceName = "DELIVERY_SEQUENCE",allocationSize = 1)
    @Column(name = "DELIVERY_ID")
    private Long id;    // 배송지번호

    private String recipient;   // 받는사람

    @Embedded
    private Address address;    //주소
    private String phoneNumber;     // 핸드폰
    private String requirements;    // 요청사항

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
