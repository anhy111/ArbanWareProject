package com.aw.arbanware.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
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
