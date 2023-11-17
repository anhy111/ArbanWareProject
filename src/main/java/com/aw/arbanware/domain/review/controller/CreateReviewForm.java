package com.aw.arbanware.domain.review.controller;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateReviewForm {
    private Long orderProductId;
    private Long productId;

    @Min(value = 1, message = "평점을 입력해주세요")
    private int rating;

    private MultipartFile[] images;

    @NotBlank(message = "리뷰내용을 입력해주세요")
    private String content;
    private Color color;
    private Size size;

    public CreateReviewForm(final Long productId) {
        this.productId = productId;
    }


    public static Review createReviewExcludingAttachFile(CreateReviewForm form, Long memberId) {
        final Member member = new Member();
        member.setId(memberId);

        final OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(form.getOrderProductId());

        final Review review = new Review();
        review.setMember(member);
        review.setContent(form.getContent());
        review.setRating(form.getRating());
        review.setOrderProduct(orderProduct);
        return review;
    }
}
