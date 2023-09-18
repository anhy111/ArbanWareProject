package com.aw.arbanware.domain.buyer;

import com.aw.arbanware.domain.common.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Buyer {
    @Id @GeneratedValue
    @Column(name = "BUYER_ID")
    private Long id;
    private String name;
    @Embedded
    private Address address;
    private String telephonedms;
    private String phoneNumber;
    private String email;
}
