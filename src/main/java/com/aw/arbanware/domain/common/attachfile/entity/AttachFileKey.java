package com.aw.arbanware.domain.common.attachfile.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AttachFileKey implements Serializable {
    private Long id;
    private int sequence;
}
