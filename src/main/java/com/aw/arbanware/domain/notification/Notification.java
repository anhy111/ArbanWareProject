package com.aw.arbanware.domain.notification;

import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime notificationTime; //알림시간

    @Enumerated(EnumType.STRING)
    private NotificationCheckYn checkYn; //확인 여부
}
