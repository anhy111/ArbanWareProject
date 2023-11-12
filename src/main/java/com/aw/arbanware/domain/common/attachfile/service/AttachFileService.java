package com.aw.arbanware.domain.common.attachfile.service;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private static final String UPLOAD_FOLDER = "C:\\arbanWare\\upload\\";

    @Transactional
    public List<AttachFile> saveAttachFiles(final MultipartFile[] multipartFiles) {
        final Long attachFileId = attachFileRepository.findSequence();
        int sequence = 1;

        List<AttachFile> attachFiles = new ArrayList<>();
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
}
