package com.aw.arbanware.domain.user.repository;

import com.aw.arbanware.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"authorization"})
    Optional<User> findUserByLoginId(String loginId);
}
