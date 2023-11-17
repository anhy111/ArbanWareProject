package com.aw.arbanware.domain.review.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class EditReviewForm {
    private Long productId;
    private Long reviewId;
    @Min(value = 1, message = "평점을 입력해주세요")
    private int rating;

    private MultipartFile[] images;

    @NotBlank(message = "리뷰내용을 입력해주세요")
    private String content;
}
