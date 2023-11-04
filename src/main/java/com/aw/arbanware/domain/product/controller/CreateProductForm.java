package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.category.entity.Category;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class CreateProductForm {
    @NotBlank(message = "상품명을 입력해주세요")
    private String name;           // 상품명

    @Min(value = 1, message = "카테고리를 선택해주세요.")
    private Long categoryId;

    @NotNull(message = "상품이미지가 없습니다.")
    private String productImages;       // 34, 35 처럼 ,구분자로 상품이미지 id가 넘어옴

    @NotNull(message = "한개 이상의 색상을 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 색상을 선택해야합니다")
    private List<Color> colors = new ArrayList<>();     // 색상

    @NotNull(message = "한개 이상의 사이즈를 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 사이즈를 선택해야합니다")
    private List<Size> sizes = new ArrayList<>();       // 사이즈

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @PositiveOrZero(message = "0 이상의 재고를 입력해주세요")
    private String inventory;           //기본재고수량

    @NotNull(message = "이미지를 선택해주세요")
    private MultipartFile thumbnail;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;    //내용

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive(message = "0 이상의 원가를 입력해주세요")
    private String  cost;           //원가

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive(message = "0 이상의 판매가를 입력해주세요")
    private String  price;          //판매가

    public static Product createProduct(CreateProductForm form) {
        final Product product = new Product();
        final Category category = new Category();
        category.setId(form.getCategoryId());

        product.setCategory(category);
        product.setName(form.getName());
        product.setCost(Integer.parseInt(form.getCost()));
        product.setPrice(Integer.parseInt(form.getPrice()));
        product.setContent(form.getContent());
        return product;
    }

    public static List<ProductInfo> createProductInfo(CreateProductForm form, final Product product) {
        List<ProductInfo> productInfos = new ArrayList<>();
        for (Size size : form.getSizes()) {
            for (Color color : form.getColors()) {
                productInfos.add(
                    new ProductInfo(
                            product, size, color, Integer.parseInt(form.getInventory())
                    )
                );
            }
        }
        return productInfos;
    }
}
