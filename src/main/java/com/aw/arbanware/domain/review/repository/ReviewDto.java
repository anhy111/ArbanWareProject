package com.aw.arbanware.domain.review.repository;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewDto {
    private int rating; //별점
    private LocalDateTime modificationTime;
    private LocalDateTime registrationTime;     //등록시간
    private String name;    // 회원이름
    private String content; //내용
    private Long attachFileId;  // 첨부파일아이디
    private Color color;
    private Size size;
    private List<AttachFileInfo> images;  //첨부파일들

    public ReviewDto(final int rating, final LocalDateTime modificationTime, final LocalDateTime registrationTime, final String name,
                     final String content, final Color color, final Size size, final Long attachFileId) {
        this.rating = rating;
        this.modificationTime = modificationTime;
        this.registrationTime = registrationTime;
        this.name = name;
        this.content = content;
        this.color = color;
        this.size = size;
        this.attachFileId = attachFileId;
    }
}
