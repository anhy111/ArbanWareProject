package com.aw.arbanware.domain.user.repository;

import com.aw.arbanware.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    @EntityGraph(attributePaths = {"authorization"})
    Optional<T> findUserByLoginId(String loginId);

}
