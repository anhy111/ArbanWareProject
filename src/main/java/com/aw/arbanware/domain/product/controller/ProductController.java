package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.common.apiobj.CkEditor5RespForm;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import com.aw.arbanware.domain.product.service.ProductImageService;
import com.aw.arbanware.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;

    @Value("${arbanWare.upload.url}")
    private String uploadUrl;

    @GetMapping("/products")
    public String products() {
        return "page/product/products";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
//        final Optional<Product> findProduct = productService.findById(1L);
//        model.addAttribute("product", findProduct.get());
        model.addAttribute("product", new Product());
        model.addAttribute("productInfo", new ProductInfo());
        return "page/product/createProductForm";
    }

    @PostMapping("/products/imageUpload")
    @ResponseBody
    public ResponseEntity<List<CkEditor5RespForm>> imageUpload(MultipartFile[] images, HttpServletRequest request) {
        log.info("images = {}", images);
        final List<ProductImage> productImages = productImageService.saveAll(images);
        List<CkEditor5RespForm> forms = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            final CkEditor5RespForm ckEditor5RespForm = new CkEditor5RespForm();
            final Map<String, String> urls = ckEditor5RespForm.getUrls();
            final String domain = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            final AttachFileInfo attachFileInfo = productImage.getAttachFileInfo();
            urls.put("default", domain + uploadUrl + attachFileInfo.getStoredPath() + attachFileInfo.getStoredFileName());
            forms.add(ckEditor5RespForm);
        }
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
}
