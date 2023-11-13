package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.common.apiobj.CkEditor5RespForm;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.repository.ProductProductInfoDto;
import com.aw.arbanware.domain.product.service.ProductImageService;
import com.aw.arbanware.domain.product.service.ProductInfoService;
import com.aw.arbanware.domain.product.service.ProductService;
import com.aw.arbanware.domain.review.controller.CreateReviewForm;
import com.aw.arbanware.domain.review.entity.Review;
import com.aw.arbanware.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductInfoService productInfoService;
    private final ProductImageService productImageService;
    private final ReviewService reviewService;

    private final Color[] colorValues = Color.values();
    private final Size[] sizeValues = Size.values();
    private static final int reviewShowPageNum = 5;
    private static final int productShowPageNum = 5;

    @ModelAttribute("colorValues")
    public Color[] colorValues() {
        return colorValues;
    }
    @ModelAttribute("sizeValues")
    public Size[] sizeValues() {
        return sizeValues;
    }


    @Value("${arbanWare.upload.url}")
    private String uploadUrl;

    @GetMapping("/products")
    public String products(@Valid  @ModelAttribute("condition") ProductSearchCondition condition,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam(required = false) boolean deleteProduct) {
        if (bindingResult.hasFieldErrors("minPrice") || bindingResult.hasFieldErrors("maxPrice")) {
            bindingResult.reject("price","숫자만 입력해주세요.");
            return "page/product/products";
        }

        final long startTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();

        if (!StringUtils.hasText(condition.getSortProperty())) {
            condition.setSortProperty("registrationTime");
        }

        final PageRequest pageRequest = PageRequest.of(condition.getPage(), condition.getPageSize(), Sort.Direction.DESC, condition.getSortProperty());
        final Page<ProductProductInfoDto> pageProduct = productService.searchProducts(condition, pageRequest);
        log.info("condition = {}", condition);

        int currentPage = pageProduct.getNumber();
        int totalPage = pageProduct.getTotalPages();
        int startPage = getStartPage(currentPage, productShowPageNum);

        // 마지막페이지 처리
        int endPage = getEndPage(totalPage, startPage, productShowPageNum);
        final long endTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        log.info("products목록 경과시간 = {}ms -> {}초", endTime - startTime, (endTime - startTime)/(double)1000);


        model.addAttribute("products", pageProduct.getContent());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", pageProduct);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("deleteProduct", deleteProduct);

        return "page/product/products";
    }


    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model,
                                @PageableDefault(size = 5, page = 0, sort = "registrationTime") Pageable pageable,
                                @RequestParam(required = false) boolean addProduct,
                                @RequestParam(required = false) boolean editProduct,
                                @RequestParam(required = false) boolean addReview) {
        final List<ProductInfo> findProductInfos = productInfoService.findByProductId(id);
        if (findProductInfos.isEmpty()) {
            return "page/product/notFoundProduct";
        }

        final Page<Review> pageReviews = reviewService.findByProduct(id, pageable);
        int totalPage = pageReviews.getTotalPages();
        int currentPage = pageReviews.getNumber();
        int startPage = getStartPage(currentPage, reviewShowPageNum);
        int endPage = getEndPage(totalPage, startPage, reviewShowPageNum);

        List<Color> existsColors = existsColorsAndSorted(findProductInfos);
        List<Size> existsSizes = existsSizesAndSorted(findProductInfos);

        model.addAttribute("addProduct", addProduct);
        model.addAttribute("editProduct", editProduct);
        model.addAttribute("addReview", addReview);
        model.addAttribute("product", findProductInfos.get(0).getProduct());
        model.addAttribute("colors", existsColors);
        model.addAttribute("sizes", existsSizes);
        model.addAttribute("productInfos", findProductInfos);
        model.addAttribute("form", new OrderProductForm());
        model.addAttribute("reviewForm", new CreateReviewForm(id));
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", pageReviews);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "page/product/productDetail";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
        model.addAttribute("product", new CreateProductForm());
        return "page/product/createProductForm";
    }

    @PostMapping("/products/new")
    public String newProductsPost(@Validated @ModelAttribute("product") CreateProductForm form,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        log.info("createProductForm = {}", form);
        if (form.getThumbnail().isEmpty()) {
            bindingResult.rejectValue("thumbnail", "thumbnailNotNull");
        }

        if (bindingResult.hasErrors()) {
            return "page/product/createProductForm";
        }

        final Product product = productService.addProduct(form);
        redirectAttributes.addAttribute("addProduct", true);
        return "redirect:/products/" + product.getId();
    }

    @GetMapping("/products/{id}/edit")
    public String updateProducts(@PathVariable Long id, Model model) {
        final UpdateProductForm updateForm = productInfoService.findUpdateFormByProductId(id);
        if (updateForm == null) {
            return "page/product/notFoundProduct";
        }

        model.addAttribute("product", updateForm);
        return "page/product/updateProductForm";
    }

    @PostMapping("/products/{id}/edit")
    public String updateProductsPost(@Validated @ModelAttribute("product") UpdateProductForm form,
                                    BindingResult bindingResult,
                                    @PathVariable Long id,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        log.info("form = {}", form);
        if (form.getThumbnail().getSize() > 1000000) {
            bindingResult.rejectValue("thumbnail", "thumbnail");
        }

        if (bindingResult.hasErrors()) {
            return "page/product/updateProductForm";
        }

        productService.updateProduct(form);
        redirectAttributes.addAttribute("editProduct", true);
        return "redirect:/products/" + id;
    }

    @GetMapping("/products/{id}/delete")
    public String deleteProducts(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addAttribute("deleteProduct", true);
        return "redirect:/products";
    }

    @PostMapping("/products/imageUpload")
    @ResponseBody
    public ResponseEntity<List<CkEditor5RespForm>> imageUpload(MultipartFile[] images, HttpServletRequest request) {
        log.info("images = {}", images);
        final List<ProductImage> productImages = productImageService.saveAll(images);
        List<CkEditor5RespForm> forms = new ArrayList<>();

        for (ProductImage productImage : productImages) {
            final CkEditor5RespForm ckEditor5RespForm = new CkEditor5RespForm();
            ckEditor5RespForm.setImageId(productImage.getId());

            final Map<String, String> urls = ckEditor5RespForm.getUrls();
            final String domain = request.getRequestURL().toString().replace(request.getRequestURI(), "");

            final AttachFileInfo attachFileInfo = productImage.getAttachFileInfo();
            urls.put("default", domain + uploadUrl + attachFileInfo.getStoredPath() + attachFileInfo.getStoredFileName());

            forms.add(ckEditor5RespForm);
        }
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    private List<Color> existsColorsAndSorted(final List<ProductInfo> productInfos) {
        Set<Color> set = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            set.add(productInfo.getColor());
        }

        final List<Color> colors = new ArrayList<>(set);
        colors.sort(
                (o1, o2) -> o1.ordinal() - o2.ordinal()
        );

        return colors;
    }

    private List<Size> existsSizesAndSorted(final List<ProductInfo> productInfos) {
        Set<Size> set = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            set.add(productInfo.getSize());
        }

        final List<Size> sizes = new ArrayList<>(set);
        sizes.sort(
                (o1, o2) -> o1.ordinal() - o2.ordinal()
        );

        return sizes;
    }

    private static int getEndPage(final int totalPage, final int startPage, final int showPageNum) {
        int endPage = startPage + showPageNum - 1;
        if (endPage > totalPage) {
            endPage = totalPage - 1;
        }
        return endPage;
    }

    private static int getStartPage(final int currentPage, int showPageNum) {
        int startPage = 0;
        while (startPage + showPageNum <= currentPage) {
            startPage += showPageNum;
        }
        return startPage;
    }
}
