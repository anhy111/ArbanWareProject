package com.aw.arbanware.domain.inquiry;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Inquiry {

    @Id @GeneratedValue
    @Column(name = "INQUIRY_ID")
    private Long id; //문의 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //문의자

    private String title; //제목
    private String content; //내용
    private Long attachFileId; //첨부파일 번호
    private String type; //분류
    private LocalDateTime inquiryTime; //문의 시간

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부



}
