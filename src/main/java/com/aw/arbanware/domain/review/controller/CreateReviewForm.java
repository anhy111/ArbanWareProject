package com.aw.arbanware.domain.review.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@ToString
public class CreateReviewForm {
    private int rating;
    private MultipartFile[] images;
    private String content;
}
