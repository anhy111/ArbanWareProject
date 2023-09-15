package com.aw.arbanware.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registrationTime;

    @LastModifiedDate
    private LocalDateTime modificationTime;

    @Override
    public String toString() {
        return "BaseTimeEntity{" +
                "registrationTime=" + registrationTime +
                ", modificationTime=" + modificationTime +
                '}';
    }
}
