package com.aw.arbanware.domain.notice.entity;

import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Notice extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "notice_seq", sequenceName = "NOTICE_SEQUENCE", allocationSize = 1)
    @Column(name = "NOTICE_ID")
    private Long id; //공지사항 번호

    private String title; //제목

    @Column(columnDefinition = "TEXT")
    private String content; //내용

    private Long attachFileId; // 첨부파일 번호

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn = DeleteYn.N; //삭제여부


}
