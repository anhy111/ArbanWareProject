package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.category.service.CategoryService;
import com.aw.arbanware.domain.common.apiobj.CkEditor5RespForm;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.service.ProductImageService;
import com.aw.arbanware.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
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
    public String products(Model model,
                           @PageableDefault(size = 12, sort = "id",
                               direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<Product> pageProduct = productService.findByAll(pageable);
        model.addAttribute("products", pageProduct.getContent());
        model.addAttribute("totalPage", pageProduct.getTotalPages());
        return "page/product/products";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model,
                                @RequestParam(required = false) boolean addProduct) {
        final Optional<Product> findProduct = productService.findById(id);
        if (findProduct.isEmpty()) {
            return "page/product/notFoundProduct";
        }
        model.addAttribute("product", findProduct.get());
        model.addAttribute("addProduct", addProduct);
        return "page/product/productDetail";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
//        final Optional<Product> findProduct = productService.findById(1L);
//        model.addAttribute("product", findProduct.get());
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "page/product/createProductForm";
        }

        final Product product = productService.addProduct(form);
        redirectAttributes.addAttribute("addProduct", true);
        return "redirect:/products/" + product.getId();
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
}
