package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/products")
    public String products() {
        return "page/product/products";
    }

    @GetMapping("/products/new")
    public String newProducts(Model model) {
//        final Optional<Product> findProduct = productRepository.findById(1L);
//        model.addAttribute("product", findProduct.get());
        model.addAttribute("product", new Product());
        return "page/product/createProductForm";
    }
}
