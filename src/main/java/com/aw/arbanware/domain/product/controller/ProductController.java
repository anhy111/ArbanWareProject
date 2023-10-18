package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/products")
    public String products() {
        return "page/product/products";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
        final Optional<Product> findProduct = productRepository.findById(1L);
        model.addAttribute("product", findProduct.get());
//        model.addAttribute("product", new Product());
        return "page/product/createProductForm";
    }

    @PostMapping("/products/imageUpload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> imageUpload(MultipartFile[] images) {
        log.info("images = {}", images);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
