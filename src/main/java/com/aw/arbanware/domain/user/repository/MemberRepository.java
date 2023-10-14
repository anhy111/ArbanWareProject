package com.aw.arbanware.domain.user.repository;

import com.aw.arbanware.domain.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.loginId = :loginId " +
            "and m.name = :name and m.email = :email")
    Optional<Member> findSearchPasswordEmailMember(@Param("loginId") String loginId,
                                                   @Param("name") String name,
                                                   @Param("email") String email);

    @Query("select m from Member m where m.loginId = :loginId " +
            "and m.name = :name and m.phoneNumber = :phoneNumber")
    Optional<Member> findSearchPasswordPhoneMember(@Param("loginId") String loginId,
                                                   @Param("name") String name,
                                                   @Param("phoneNumber") String phoneNumber);
}
