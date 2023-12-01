package com.aw.arbanware.domain;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.controller.ProductSearchCondition;
import com.aw.arbanware.domain.product.repository.ProductProductInfoDto;
import com.aw.arbanware.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    private final Color[] colorValues = Color.values();

    @ModelAttribute("colorValues")
    public Color[] colorValues() {
        return colorValues;
    }

    @GetMapping("/")
    public String main(Model model){

        final PageRequest newProductRequest = PageRequest.of(0, 4, Sort.Direction.DESC, "registrationTime");
        final Page<ProductProductInfoDto> newProducts = productService.searchProducts(new ProductSearchCondition(), newProductRequest);

        final PageRequest bestProductRequest = PageRequest.of(0, 4, Sort.Direction.DESC, "buyCount");
        final Page<ProductProductInfoDto> bestProducts = productService.searchProducts(new ProductSearchCondition(), bestProductRequest);

        model.addAttribute("newProducts", newProducts);
        model.addAttribute("bestProducts", bestProducts);
        return "page/index";
    }
}
