package com.aw.arbanware.domain.review.entity;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "comment_seq")
//    @SequenceGenerator(name = "comment_seq",sequenceName = "COMMENT_SEQUENCE",allocationSize = 1)
    @Column(name = "COMMENT_ID")
    private Long id;    //댓글번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;  // 후기번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  // 회원번호

    @Column(columnDefinition = "TEXT")
    private String content; //내용
}
