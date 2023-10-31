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

@Getter
@Setter
@ToString
public class UpdateProductForm {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요")
    private String name;           // 상품명

    @Min(value = 1, message = "카테고리를 선택해주세요.")
    private Long categoryId;

    private String productImages;       // 34, 35 처럼 ,구분자로 상품이미지 id가 넘어옴

    @NotNull(message = "한개 이상의 색상을 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 색상을 선택해야합니다")
    private List<Color> colors = new ArrayList<>();     // 색상

    @NotNull(message = "한개 이상의 사이즈를 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 사이즈를 선택해야합니다")
    private List<Size> sizes = new ArrayList<>();       // 사이즈

    @NotNull(message = "이미지를 선택해주세요")
    private MultipartFile thumbnail;

    private String thumbnailStr;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;    //내용

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive(message = "양수만 입력해주세요")
    private String  cost;           //원가

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive(message = "양수만 입력해주세요")
    private String  price;          //판매가

    public static Product createProduct(UpdateProductForm form) {
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

    public static List<ProductInfo> createProductInfo(UpdateProductForm form, final Product product) {
        List<ProductInfo> productInfos = new ArrayList<>();
        for (Size size : form.getSizes()) {
            for (Color color : form.getColors()) {
                productInfos.add(new ProductInfo(product, size, color, -1));
            }
        }
        return productInfos;
    }

    public static UpdateProductForm createUpdateForm(List<ProductInfo> productInfos) {
        final UpdateProductForm updateProductForm = new UpdateProductForm();
        if (productInfos.isEmpty()) {
            return null;
        }
        final List<Color> formColors = updateProductForm.getColors();
        final List<Size> formSizes = updateProductForm.getSizes();
        for (ProductInfo productInfo : productInfos) {
            formColors.add(productInfo.getColor());
            formSizes.add(productInfo.getSize());
        }

        final Product product = productInfos.get(0).getProduct();
        updateProductForm.setCost(String.valueOf(product.getCost()));
        updateProductForm.setName(product.getName());
        updateProductForm.setContent(product.getContent());
        updateProductForm.setPrice(String.valueOf(product.getPrice()));
        updateProductForm.setCategoryId(product.getCategory().getId());
        updateProductForm.setId(product.getId());
        updateProductForm.setThumbnailStr(product.getThumbnail());
        return updateProductForm;
    }
}
