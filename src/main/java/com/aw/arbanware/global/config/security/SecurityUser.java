package com.aw.arbanware.global.config.security;

import com.aw.arbanware.domain.user.entity.Admin;
import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private final Long id;
    private final String name; // name은 이름, username은 loginId


    public SecurityUser(User user, List<GrantedAuthority> authorities) {
        super(user.getLoginId(), user.getLoginPassword(), authorities);
        this.id = user.getId();
        if (user instanceof Member) {
            this.name = ((Member) user).getName();
        } else {
            this.name = ((Admin) user).getName();
        }
    }
}
