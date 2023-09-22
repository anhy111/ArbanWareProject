package com.aw.arbanware.domain.authorization;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
@Table(name = "AUTHORIZATIONS")
public class Authorization extends BaseTimeEntity implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // 사용자번호

    @Id
    private String auth;    // 권한

    public Authorization() {
    }

    public Authorization(final String auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Authorization that = (Authorization) o;
        return Objects.equals(user, that.user) && Objects.equals(auth, that.auth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, auth);
    }

    @Override
    public String toString() {
        return String.format("auth: %s", auth);
    }
}
