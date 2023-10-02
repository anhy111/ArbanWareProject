package com.aw.arbanware.domain.notice.entity;

import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.user.entity.Admin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Notice extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "NOTICE_ID")
    private Long id; //공지사항 번호

    private String title; //제목
    private String content; //내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin;

    private Long attachFileId; // 첨부파일 번호

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부


}
