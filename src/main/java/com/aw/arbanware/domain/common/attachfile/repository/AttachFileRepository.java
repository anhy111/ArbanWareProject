package com.aw.arbanware.domain.common.attachfile.repository;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.entity.AttachFileKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachFileRepository extends JpaRepository<AttachFile, AttachFileKey> {
}
