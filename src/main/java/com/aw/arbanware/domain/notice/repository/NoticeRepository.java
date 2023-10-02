package com.aw.arbanware.domain.notice.repository;

import com.aw.arbanware.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

//    @Override
//    @EntityGraph(attributePaths = {"admin"})
//    List<Notice> findAll();

}
