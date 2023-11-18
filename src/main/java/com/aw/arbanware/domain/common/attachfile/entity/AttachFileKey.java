package com.aw.arbanware.domain.common.attachfile.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AttachFileKey implements Serializable {
    private Long id;
    private int sequence;

    public AttachFileKey(Long id, int sequence) {
        this.id = id;
        this.sequence = sequence;
    }
}
