package com.aw.arbanware.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Admin extends User {
    private String name;
}
