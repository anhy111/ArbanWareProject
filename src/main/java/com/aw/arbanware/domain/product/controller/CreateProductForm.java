package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class CreateProductForm {
    @NotBlank(message = "상품명을 입력해주세요")
    private String name;           // 상품명

    @NotNull(message = "한개 이상의 색상을 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 색상을 선택해야합니다")
    private List<Color> colors = new ArrayList<>();     // 색상

    @NotNull(message = "한개 이상의 사이즈를 선택해야합니다")
    @javax.validation.constraints.Size(min = 1, message = "한개 이상의 사이즈를 선택해야합니다")
    private List<Size> sizes = new ArrayList<>();       // 사이즈

    @NotNull(message = "이미지를 선택해주세요")
    private MultipartFile thumbnail;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;    //내용

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive
    private String  cost;           //원가

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    @Positive
    private String  price;          //판매가
}
