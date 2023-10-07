package com.aw.arbanware.infra.email;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Recipient {
    private String address;
    private String name;
    private String type;

    public Recipient() {
    }
}
