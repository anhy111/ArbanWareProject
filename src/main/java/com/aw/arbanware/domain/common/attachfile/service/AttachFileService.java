package com.aw.arbanware.domain.common.attachfile.service;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.entity.AttachFileKey;
import com.aw.arbanware.domain.common.attachfile.repository.AttachFileRepository;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private static final String UPLOAD_FOLDER = "C:" + File.separator + "arbanWare" + File.separator + "upload" + File.separator;

    @Transactional
    public List<AttachFile> saveAttachFiles(final MultipartFile[] multipartFiles) {
        final Long attachFileId = attachFileRepository.findSequence();
        int sequence = 1;

        List<AttachFile> attachFiles = new ArrayList<>();

        if (multipartFiles == null || multipartFiles.length == 0 || multipartFiles[0].getSize() <= 0) {
            return attachFiles;
        }

        String uploadPath = AttachFileInfo.todayToFolder();

        for (MultipartFile multipartFile : multipartFiles) {

            final AttachFile attachFile = new AttachFile(attachFileId, sequence++, new AttachFileInfo(multipartFile, uploadPath));
            attachFile.setAttachFileInfo(new AttachFileInfo(multipartFile, uploadPath));
            log.info("{} 파일 업로드 시작", attachFile.getAttachFileInfo().getOriginalFileName());

            final File saveFile = new File(UPLOAD_FOLDER + uploadPath + attachFile.getAttachFileInfo().getStoredFileName());
            if (saveFile.mkdirs()) {
                try {
                    multipartFile.transferTo(saveFile);
                    attachFiles.add(attachFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("첨부파일 폴더 생성 실패");
            }
        }
        return attachFileRepository.saveAll(attachFiles);
    }

    public Map<Long, List<AttachFile>> findByIds(List<Long> ids) {
        final List<AttachFile> findAttachFile = attachFileRepository.findByIds(ids);
        return findAttachFile.stream().collect(Collectors.groupingBy(AttachFile::getId));
    }


    @Transactional
    public List<AttachFile> updateAttachFiles(final Long id, MultipartFile[] files, int sequence) {
        List<AttachFile> attachFiles = new ArrayList<>();

        String uploadPath = AttachFileInfo.todayToFolder();
        for (MultipartFile file : files) {

            final AttachFile attachFile = new AttachFile(id, sequence++, new AttachFileInfo(file, uploadPath));
            log.info("{} 파일 업로드 시작", attachFile.getAttachFileInfo().getOriginalFileName());

            final File saveFile = new File(UPLOAD_FOLDER + uploadPath + attachFile.getAttachFileInfo().getStoredFileName());
            if (saveFile.mkdirs()) {
                try {
                    file.transferTo(saveFile);
                    attachFiles.add(attachFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("첨부파일 폴더 생성 실패");
            }
        }
        return attachFileRepository.saveAll(attachFiles);
    }

    public int findFileSequenceById(final Long id) {
        return attachFileRepository.findFileSequenceById(id);
    }

    public void deleteAttachFiles(final Long id) {
        final List<AttachFile> findAttachFiles = attachFileRepository.findById(id);
        List<AttachFileKey> attachFileKeys = new ArrayList<>();
        for (AttachFile attachFile : findAttachFiles) {
            final File file = new File(UPLOAD_FOLDER + attachFile.getAttachFileInfo().getStoredPath() + attachFile.getAttachFileInfo().getStoredFileName());
            if (file.exists()) {
                if(file.delete()){
                    log.info("첨부파일 삭제 성공");
                }else{
                    log.info("첨부파일 삭제 실패");
                }
            }
            attachFileKeys.add(new AttachFileKey(attachFile.getId(), attachFile.getSequence()));
        }
        attachFileRepository.deleteAllByIdInBatch(attachFileKeys);
    }
}
