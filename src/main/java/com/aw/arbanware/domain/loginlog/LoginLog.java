package com.aw.arbanware.domain.loginlog;

import com.aw.arbanware.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class LoginLog {
    @Id @GeneratedValue
    @Column(name = "LOGIN_LOG_ID")
    private Long id;    //로그인로그번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;  // 사용자번호
    
    @Enumerated(EnumType.STRING)
    private LoginStatus loginSuccess; //로그인성공 여부   


    private String loginId; //로그인 아이디
    private String loginPassword;   // 로그인 패스워드
    private String loginFailReason; // 로그인 실패 이유

    private String ip;  // ip
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime loginTime;    // 로그인 시간
}
