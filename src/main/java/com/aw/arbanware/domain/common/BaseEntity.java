package com.aw.arbanware.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;

@Getter
public abstract class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(updatable = false)
    private String registrant;

    @LastModifiedBy
    private String modifier;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "registrant='" + registrant + '\'' +
                ", modifier='" + modifier + '\'' +
                "} " + super.toString();
    }
}
