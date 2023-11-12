package com.aw.arbanware.domain.review.service;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.service.AttachFileService;
import com.aw.arbanware.domain.review.controller.CreateReviewForm;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final AttachFileService attachFileService;
    private final ReviewRepository reviewRepository;


    @Transactional
    public boolean createReview(final CreateReviewForm form, Long memberId) {
        // 폼 -> 엔티티 변환
        final Review formReview = CreateReviewForm.createReviewExcludingAttachFile(form, memberId);
        // 첨부파일 파일, DB 저장
        final List<AttachFile> attachFiles = attachFileService.saveAttachFiles(form.getImages());

        // 첨부파일 저장에 성공하면 save
        if (attachFiles.size() == form.getImages().length && attachFiles.size() > 0) {
            formReview.setAttachFileId(attachFiles.get(0).getId());
            reviewRepository.save(formReview);
            return true;
        }
        return false;
    }
}
