package com.aw.arbanware.domain.common.attachfile.entity;

import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@IdClass(AttachFileKey.class)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AttachFile implements Persistable<Long> {
    @Id
    @Column(name = "ATTACH_FILE_ID")
    private Long id;    // 첨부파일번호

    @Id
    private int sequence;   // 순번

    @Embedded
    private AttachFileInfo attachFileInfo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    public AttachFile(final Long id, final int sequence, final AttachFileInfo attachFileInfo) {
        this.id = id;
        this.sequence = sequence;
        this.attachFileInfo = attachFileInfo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AttachFile that = (AttachFile) o;
        return sequence == that.sequence && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequence);
    }

    @Override
    public boolean isNew() {
        return createdTime == null;
    }
}
