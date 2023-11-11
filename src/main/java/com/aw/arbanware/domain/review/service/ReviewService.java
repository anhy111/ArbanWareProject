package com.aw.arbanware.domain.review.service;

import com.aw.arbanware.domain.review.controller.CreateReviewForm;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public Review createReview(final CreateReviewForm form, Long memberId) {

        final Review formReview = CreateReviewForm.createReviewExcludingAttachFile(form, memberId);

        return null;
    }
}
