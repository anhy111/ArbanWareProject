package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.category.service.CategoryService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    private final CategoryService categoryService;

    private final Color[] colorValues = Color.values();
    private final Size[] sizeValues = Size.values();

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
        log.info("{}, produts 시작", startTime);

        if (!StringUtils.hasText(condition.getSortProperty())) {
            condition.setSortProperty("registrationTime");
        }

        final PageRequest pageRequest = PageRequest.of(condition.getPage(), condition.getPageSize(), Sort.Direction.DESC, condition.getSortProperty());
        final Page<ProductProductInfoDto> pageProduct = productService.searchProducts(condition, pageRequest);
        log.info("condition = {}", condition);

        // 페이징번호 처리
        int startPage = 0;
        int currentPage = pageProduct.getNumber();
        int totalPage = pageProduct.getTotalPages();
        while (startPage + 5 <= currentPage) {
            startPage += 5;
        }

        // 마지막페이지 처리
        int endPage = startPage + 4;
        if (endPage > totalPage) {
            endPage = totalPage - 1;
        }
        final long endTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        log.info("{}, products 종료", endTime);
        log.info("경과시간 = {}ms -> {}초", endTime - startTime, (endTime - startTime)/(double)1000);

        log.info("currentPage = {}", currentPage);
        log.info("startPage = {}", startPage);
        log.info("endPage = {}", endPage);

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
                                @RequestParam(required = false) boolean addProduct,
                                @RequestParam(required = false) boolean editProduct) {
        final List<ProductInfo> findProductInfos = productInfoService.findByProductId(id);
        if (findProductInfos.isEmpty()) {
            return "page/product/notFoundProduct";
        }

        List<Color> existsColors = existsColorsAndSorted(findProductInfos);
        List<Size> existsSizes = existsSizesAndSorted(findProductInfos);

        model.addAttribute("product", findProductInfos.get(0).getProduct());
        model.addAttribute("colors", existsColors);
        model.addAttribute("sizes", existsSizes);
        model.addAttribute("productInfos", findProductInfos);
        model.addAttribute("form", new OrderProductForm());
        model.addAttribute("addProduct", addProduct);
        model.addAttribute("editProduct", editProduct);
        return "page/product/productDetail";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
        model.addAttribute("product", new CreateProductForm());
        model.addAttribute("categories", categoryService.findAllCategories());
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
            model.addAttribute("categories", categoryService.findAllCategories());
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
        model.addAttribute("categories", categoryService.findAllCategories());
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
            model.addAttribute("categories", categoryService.findAllCategories());
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
}
