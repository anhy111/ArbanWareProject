package com.aw.arbanware.domain.review.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.service.OrderProductService;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.service.ProductInfoService;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.service.ReviewService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final ProductInfoService productInfoService;
    private final OrderProductService orderProductService;

    @PostMapping("/new")
    public String newReview(@ModelAttribute CreateReviewForm form,
                            SecurityUser user,
                            RedirectAttributes redirectAttributes) {
        log.info("form = {}", form);
        final boolean addReview = reviewService.createReview(form, user.getId());

        redirectAttributes.addAttribute("addReview", addReview);
        return "redirect:/products/" + form.getProductId();
    }

    @GetMapping("/orderCheck")
    public ResponseEntity<DefaultResponse<Long>> orderCheck(@RequestParam Long productId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(new DefaultResponse<>(StatusCode.UNAUTHORIZED, ResponseMessage.IS_ANONYMOUS)
                    , HttpStatus.OK);
        }
        SecurityUser user =  (SecurityUser)authentication.getPrincipal();

        final Optional<OrderProduct> findOrderProduct = orderProductService.orderCheckByMember(productId, user.getId());
        return findOrderProduct
                .map(orderProduct -> new ResponseEntity<>(
                        new DefaultResponse<>(
                                StatusCode.OK, ResponseMessage.CAN_WRITE_REVIEW, orderProduct.getId()
                        ), HttpStatus.OK)
                ).orElseGet(() -> new ResponseEntity<>(
                        new DefaultResponse<>(
                                StatusCode.NO_CONTENT, ResponseMessage.NOT_ORDER
                        ), HttpStatus.OK)
                );

    }
}
