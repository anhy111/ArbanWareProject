package com.aw.arbanware.domain.review.service;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.service.AttachFileService;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.review.controller.CreateReviewForm;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.repository.ReviewDto;
import com.aw.arbanware.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final AttachFileService attachFileService;
    private final ReviewRepository reviewRepository;


    @Transactional
    public boolean createReview(final CreateReviewForm form, final Long memberId) {
        // 폼 -> 엔티티 변환
        final Review formReview = CreateReviewForm.createReviewExcludingAttachFile(form, memberId);
        // 첨부파일 파일, DB 저장
        final List<AttachFile> attachFiles = attachFileService.saveAttachFiles(form.getImages());

        // 첨부파일 저장에 성공하면 save
        if (form.getImages() == null
                || form.getImages()[0].getSize() <= 0
                || attachFiles.size() == form.getImages().length) {
            if (attachFiles.size() > 0) {
                formReview.setAttachFileId(attachFiles.get(0).getId());
            }

            reviewRepository.save(formReview);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Page<ReviewDto> findByProduct(final Long productId, final Pageable pageable) {
        final Page<ReviewDto> findReviewDtos = reviewRepository.findByProduct(productId, pageable);
        final List<Long> ids = findReviewDtos.stream().map(ReviewDto::getAttachFileId).collect(Collectors.toList());
        if (ids.size() == 0) {
            return findReviewDtos;
        }

        final Map<Long, List<AttachFile>> byIds = attachFileService.findByIds(ids);

        for (ReviewDto findReviewDto : findReviewDtos) {
            if (findReviewDto.getAttachFileId() != null) {
                final List<AttachFile> attachFiles = byIds.get(findReviewDto.getAttachFileId());
                final List<AttachFileInfo> attachFileInfos = attachFiles.stream().map(attachFile -> attachFile.getAttachFileInfo()).collect(Collectors.toList());
                findReviewDto.setImages(attachFileInfos);
            }
        }
        return findReviewDtos;
    }
}
