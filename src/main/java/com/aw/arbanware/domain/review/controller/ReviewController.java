package com.aw.arbanware.domain.review.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.service.OrderProductService;
import com.aw.arbanware.domain.product.controller.ProductController;
import com.aw.arbanware.domain.product.service.ProductInfoService;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.service.ReviewService;
import com.aw.arbanware.global.config.security.SecurityUser;
import kotlinx.serialization.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final ProductInfoService productInfoService;
    private final OrderProductService orderProductService;
    private final ProductController productController;

    @PostMapping("/new")
    public String newReview(@Validated @ModelAttribute CreateReviewForm form,
                            BindingResult bindingResult,
                            SecurityUser user,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        log.info("form = {}", form);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            redirectAttributes.addAttribute("reviewTabs", true);
            redirectAttributes.addAttribute("failReviewCreate", true);
            return "redirect:/products/" + form.getProductId();
        }
        final boolean addReview = reviewService.createReview(form, user.getId());

        redirectAttributes.addAttribute("addReview", addReview);
        return "redirect:/products/" + form.getProductId();
    }

    @PostMapping("/edit")
    public String editReview(@Validated @ModelAttribute EditReviewForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        log.info("form = {}", form);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            redirectAttributes.addAttribute("reviewTabs", true);
            redirectAttributes.addAttribute("failReviewEdit", true);
            return "redirect:/products/" + form.getProductId();
        }

        if (!reviewService.updateReview(form)) {
            redirectAttributes.addAttribute("failReviewEdit", true);
            return "redirect:/products/" + form.getProductId();
        }

        redirectAttributes.addAttribute("editReview", true);
        return "redirect:/products/" + form.getProductId();
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<DefaultResponse<Long>> deleteReview(@RequestParam Long reviewId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(new DefaultResponse<>(StatusCode.UNAUTHORIZED, ResponseMessage.IS_ANONYMOUS)
                    , HttpStatus.OK);
        }
        final SecurityUser user = (SecurityUser) authentication.getPrincipal();

        final Optional<Review> findReview = reviewService.findById(reviewId);
        if (findReview.isEmpty()) {
            return new ResponseEntity<>(new DefaultResponse<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEW)
                    , HttpStatus.OK);
        }

        final Review review = findReview.get();
        if (!review.getMember().getId().equals(user.getId())) {
            return new ResponseEntity<>(new DefaultResponse<>(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN_REVIEW_DELETE)
                    , HttpStatus.OK);
        }

        reviewService.deleteReview(review);
        return new ResponseEntity<>(new DefaultResponse<>(StatusCode.OK, ResponseMessage.DELETE_REVIEW_SUCCESS)
                , HttpStatus.OK);
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
