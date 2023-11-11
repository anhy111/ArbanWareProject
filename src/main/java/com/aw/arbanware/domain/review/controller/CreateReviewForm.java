package com.aw.arbanware.domain.review.controller;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateReviewForm {
    private Long orderProductId;
    private Long productId;
    private int rating;
    private MultipartFile[] images;
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
