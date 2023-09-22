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
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginStatus loginSuccess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String loginId;
    private String loginPassword;
    private String loginFailReason;

    private String ip;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime loginTime;
}
