package com.aw.arbanware.domain.notification;

import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "NOTIFICATION_ID")
    private Long id; //알림 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //받는 사람
    
    private String title; //제목
    private String content; //내용
    private LocalDateTime notificationTime; //알림시간
    private String checkYN; //확인 여부
}
