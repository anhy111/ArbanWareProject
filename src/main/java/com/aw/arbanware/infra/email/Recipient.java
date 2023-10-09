package com.aw.arbanware.infra.email;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString(of = {"address","name"})
public class Recipient {
    private String address;
    private String name;
    private String type;

    public Recipient() {
    }
}
