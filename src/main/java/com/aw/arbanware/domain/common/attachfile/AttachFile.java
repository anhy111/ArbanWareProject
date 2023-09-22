package com.aw.arbanware.domain.common.attachfile;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(AttachFileKey.class)
@Getter @Setter
public class AttachFile extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "ATTACH_FILE_ID")
    private Long id;    // 첨부파일번호

    @Id
    private int sequence;   // 순번

    private String storedName;  // 저장파일명
    private String originalName;    // 원본파일명
    private String storedPath;  //저장경로
    private String extensionName;   // 확장자명
    private int fileSize;   // 파일크기

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
}
