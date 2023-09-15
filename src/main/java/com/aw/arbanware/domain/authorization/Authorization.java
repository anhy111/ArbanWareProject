package com.aw.arbanware.domain.authorization;

import com.aw.arbanware.domain.common.BaseTimeEntity;
import com.aw.arbanware.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private User user;

    @Id
    private String auth;

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
