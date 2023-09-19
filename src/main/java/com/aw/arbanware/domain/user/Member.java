package com.aw.arbanware.domain.user;

import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.delivery.Delivery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DiscriminatorValue("MEMBER")
@PrimaryKeyJoinColumn(name = "MEMBER_ID")
public class Member extends User {

    private String name;
    @Embedded
    private Address address;
    private String telephonedms;
    private String phoneNumber;
    private String email;
    private String birth;
    private int mileage;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", telephonedms='" + telephonedms + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", mileage=" + mileage +
                "} " + super.toString();
    }
}
