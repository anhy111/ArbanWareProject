package com.aw.arbanware.domain.user.entity;

import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.authorization.Authorization;
import com.aw.arbanware.domain.common.DeleteYn;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="UTYPE")
@Table(name = "USERS")
@Getter
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    private String loginId;
    private String loginPassword;

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn;

    @OneToMany(mappedBy = "user")
    private Set<Authorization> authorization = new HashSet<>();


    public void setId(final Long id) {
        this.id = id;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    public void setLoginPassword(final String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void setDeleteYn(final DeleteYn deleteYn) {
        this.deleteYn = deleteYn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", deleteYn=" + deleteYn +
                ", authorization=" + authorization +
                "} " + super.toString();
    }

    // 연관관계 편의 메서드
    public void addAuthorization(final Authorization authorization) {
        authorization.setUser(this);
        this.authorization.add(authorization);
    }
}