package com.aw.arbanware.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "ADMIN_ID")
public class Admin extends User {
    private String name;
}
