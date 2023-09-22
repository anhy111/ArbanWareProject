package com.aw.arbanware.domain.common.baseentity;

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
    private LocalDateTime registrationTime;     //등록시간

    @LastModifiedDate
    private LocalDateTime modificationTime;     // 수정시간

    @Override
    public String toString() {
        return "BaseTimeEntity{" +
                "registrationTime=" + registrationTime +
                ", modificationTime=" + modificationTime +
                '}';
    }
}
