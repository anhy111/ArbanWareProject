package com.aw.arbanware.domain.common.baseentity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(updatable = false)
    private String registrant;  // 등록자

    @LastModifiedBy
    private String modifier;    // 수정자

    @Override
    public String toString() {
        return "BaseEntity{" +
                "registrant='" + registrant + '\'' +
                ", modifier='" + modifier + '\'' +
                "} " + super.toString();
    }
}
