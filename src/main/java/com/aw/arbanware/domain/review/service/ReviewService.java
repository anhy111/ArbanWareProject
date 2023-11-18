package com.aw.arbanware.domain.review.service;

import com.aw.arbanware.domain.common.attachfile.entity.AttachFile;
import com.aw.arbanware.domain.common.attachfile.service.AttachFileService;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.review.controller.CreateReviewForm;
import com.aw.arbanware.domain.review.controller.EditReviewForm;
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
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final AttachFileService attachFileService;
    private final ReviewRepository reviewRepository;


    public Optional<Review> findById(final Long id) {
        return reviewRepository.findById(id);
    }

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

    @Transactional
    public boolean updateReview(final EditReviewForm form) {
        // 엔티티 조회
        log.info("리뷰 수정 시작");
        final Optional<Review> findReview = reviewRepository.findById(form.getReviewId());
        if (findReview.isEmpty()) {
            log.info("리뷰 조회 실패");
            return false;
        }
        Review review = findReview.get();
        // 첨부파일을 제외한 정보 수정
        EditReviewForm.updateReviewExcludingAttachFile(form, review);

        // 첨부파일 미포함시엔 수정 종료
        if (form.getImages() == null || form.getImages()[0].getSize() <= 0) {
            log.info("첨부파일 미포함, 리뷰 수정 종료");
            return true;
        }

        // 첨부파일 처리 로직(기존 첨부파일 삭제 및 새로운 첨부파일 파일과 DB 저장)
        // 첨부파일 삭제
        List<AttachFile> attachFiles;
        // 새로운 첨부파일 저장
        if (review.getAttachFileId() == null) {
            attachFiles = attachFileService.saveAttachFiles(form.getImages());
            if (attachFiles.size() > 0) {
                review.setAttachFileId(attachFiles.get(0).getId());
            }
        } else {
            final int sequence = attachFileService.findFileSequenceById(review.getAttachFileId());
            attachFileService.deleteAttachFiles(review.getAttachFileId());
            attachFiles = attachFileService.updateAttachFiles(review.getAttachFileId(), form.getImages(), sequence);
        }
        // 첨부파일 저장에 성공하면 save
        if (attachFiles.size() == form.getImages().length) {
            log.info("첨부파일 저장 실패");
            return true;
        }
        log.info("성공적인 리뷰 수정");
        return false;
    }

    public void deleteReview(final Review review) {
        attachFileService.deleteAttachFiles(review.getAttachFileId());
        reviewRepository.delete(review);
    }
}
