package com.aw.arbanware.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "ADMIN_ID")
public class Admin extends User {
    private String name;
}
