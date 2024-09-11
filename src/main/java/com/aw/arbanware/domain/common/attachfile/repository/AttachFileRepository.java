package com.aw.arbanware.domain.common.attachfile.repository;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.entity.AttachFileKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachFileRepository extends JpaRepository<AttachFile, AttachFileKey> {

    @Query(value = "SELECT nextVal(\"ATTACH_FILE_SEQUENCE\") FROM DUAL", nativeQuery = true)
    Long findSequence();

    @Query("select a from AttachFile a where a.id in :ids")
    List<AttachFile> findByIds(@Param("ids") List<Long> ids);

    List<AttachFile> findById(@Param("id") Long id);

    @Query("select max(a.sequence) + 1 from AttachFile a where a.id = :id")
    int findFileSequenceById(@Param("id") Long id);
}
