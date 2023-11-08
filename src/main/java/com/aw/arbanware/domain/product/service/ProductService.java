package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.controller.CreateProductForm;
import com.aw.arbanware.domain.product.controller.ProductSearchCondition;
import com.aw.arbanware.domain.product.controller.UpdateProductForm;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.repository.ProductProductInfoDto;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final ProductInfoService productInfoService;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findByAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ProductProductInfoDto> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.search(condition, pageable);
    }

    @Transactional
    public Product addProduct(CreateProductForm form) {
        // 상품추가
        final Product product = CreateProductForm.createProduct(form);
        // 대표이미지 추가
        final ProductImage thumbnail = productImageService.saveAll(new MultipartFile[]{form.getThumbnail()})
                .get(0);
        final AttachFileInfo thumbnailFileInfo = thumbnail.getAttachFileInfo();
        product.setThumbnail(thumbnailFileInfo.getStoredPath() + thumbnailFileInfo.getStoredFileName());
        final Product saveProduct = productRepository.save(product);

        // 상품정보추가
        final List<ProductInfo> productInfos = CreateProductForm.createProductInfo(form, product);
        productInfoService.saveAll(productInfos);

        // 상품이미지 업데이트 ( 상품없는 상품이미지들과 관계맺기 )
        final List<String> StrProductImages = Arrays.stream(
                                                    form.getProductImages().split(",")
                                                ).collect(Collectors.toList());
        StrProductImages.add(thumbnail.getId().toString());
        final List<Long> productImages = new ArrayList<>();
        for (String s : StrProductImages) {
            if (StringUtils.hasText(s)) {
                productImages.add(Long.parseLong(s));
            } else {
                StrProductImages.remove(s);
            }
        }

        final List<ProductImage> findProductImages = productImageService.findByIdList(productImages);
        findProductImages.forEach(pi -> {
            pi.setProduct(product);
        });

        return saveProduct;
    }

    @Transactional
    public Product deleteProduct(final Long id) {
        final Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return null;
        }
        final Product findProduct = optionalProduct.get();
        findProduct.setDeleteYn(DeleteYn.Y);
        return findProduct;
    }


    @Transactional
    public Product updateProduct(final UpdateProductForm form) {
        final Product updateProduct = UpdateProductForm.createProduct(form);
        final List<ProductInfo> findProductInfos = productInfoService.findByProductIdWithImage(form.getId());
        if (findProductInfos.size() == 0) {
            return null;
        }
        final Product findProduct = findProductInfos.get(0).getProduct();

        // 대표이미지 변경
        if (form.getThumbnail().getSize() != 0) {
            // 기존 대표이미지 삭제
            final String[] thumbnailSplit = findProduct.getThumbnail().split("/");
            final ProductImage findProductImage = productImageService.findByStoredFileName(thumbnailSplit[thumbnailSplit.length - 1]);
            log.info("삭제예정 섬네일 findProductImage = {}", findProductImage);
            findProductImage.setProduct(null);
            productImageService.save(findProductImage);

            // 업데이트한 대표이미지로 변경
            final ProductImage thumbnail = productImageService.saveAll(new MultipartFile[]{form.getThumbnail()})
                    .get(0);
            thumbnail.setProduct(findProduct);

            final AttachFileInfo thumbnailFileInfo = thumbnail.getAttachFileInfo();
            findProduct.setThumbnail(thumbnailFileInfo.getStoredPath() + thumbnailFileInfo.getStoredFileName());
            log.info("새로운 섬네일 예정 thumbnail = {}", thumbnail);
            log.info("새로운 섬네일 updateProduct.thumbnail = {}", updateProduct.getThumbnail());
        }

        // 상품 변경
        findProduct.updateProductExceptThumbnail(updateProduct);

        final List<Color> originColors = extractColor(findProductInfos);
        final List<Size> originSizes = extractSize(findProductInfos);
        // 삭제된 상품정보
        final DiffProductInfo deletedColorAndSize = deletedColorAndSize(form.getColors(), form.getSizes(), originColors, originSizes);
        removeColorAndSize(findProductInfos, deletedColorAndSize);

        // 추가된 상품정보
        final DiffProductInfo expectAddColorAndSize = expectAddColorAndSize(form.getColors(), form.getSizes(), originColors, originSizes);
        addColorAndSize(findProductInfos, expectAddColorAndSize);


        // 내용 이미지 변경
        changeContentImage(form.getProductImages(), findProduct.getProductImages(), findProduct);
        
        return findProduct;
    }

    private void changeContentImage(final String formProductImages, final List<ProductImage> findProductImages, final Product product) {
        if (formProductImages == null) {
            return;
        }
        final List<String> formImageIds = Arrays.asList(formProductImages.split(","));
        // 삭제된 이미지변경하기
        findProductImages.stream().forEach((productImage) -> {
            if (product.getThumbnail().contains(productImage.getAttachFileInfo().getStoredFileName())) {
                log.info("섬네일 이미지는 제외 productImage = {}", productImage);
                return;
            }
            if (!formImageIds.contains(productImage.getId().toString())) {
                productImage.setProduct(null);
                productImageService.save(productImage);
                log.info("삭제될 이미지 productImage = {}", productImage);
            }
        });

        if (!StringUtils.hasText(formProductImages)) {
            return;
        }
        final List<ProductImage> productImages = productImageService.findByIdList(
                Arrays.stream(formProductImages.split(","))
                        .map(Long::parseLong).collect(Collectors.toList()));
        for (ProductImage productImage : productImages) {
            productImage.setProduct(product);
        }
    }


    // 컬러 추출
    private List<Color> extractColor(List<ProductInfo> productInfos) {
        Set<Color> setColor = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            setColor.add(productInfo.getColor());
        }
        return new ArrayList<>(setColor);
    }

    // 사이즈 추출
    private List<Size> extractSize(List<ProductInfo> productInfos) {
        Set<Size> setSize = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            setSize.add(productInfo.getSize());
        }
        return new ArrayList<>(setSize);
    }

    // 삭제될 컬러와 사이즈
    private DiffProductInfo deletedColorAndSize(List<Color> formColors, List<Size> formSizes,
                                                   List<Color> originColors, List<Size> originSizes) {
        List<Color> removeColor = new ArrayList<>();
        List<Size> removeSize = new ArrayList<>();
        for (Color originColor : originColors) {
            if (!formColors.contains(originColor)) {
                removeColor.add(originColor);
            }
        }

        for (Size originSize : originSizes) {
            if (!formSizes.contains(originSize)) {
                removeSize.add(originSize);
            }
        }
        return new DiffProductInfo(removeColor, removeSize);
    }

    // 추가될 컬러와 사이즈
    private DiffProductInfo expectAddColorAndSize(List<Color> formColors, List<Size> formSizes,
                                                  List<Color> originColors, List<Size> originSizes) {
        List<Color> addColor = new ArrayList<>();
        List<Size> addSize = new ArrayList<>();
        for (Color formColor : formColors) {
            if (!originColors.contains(formColor)) {
                addColor.add(formColor);
            }
        }

        for (Size formSize : formSizes) {
            if (!originSizes.contains(formSize)) {
                addSize.add(formSize);
            }
        }
        return new DiffProductInfo(addColor, addSize);
    }

    // 컬러와 사이즈 삭제
    private void removeColorAndSize(final List<ProductInfo> findProductInfos, final DiffProductInfo diffProductInfo) {
        final List<Color> removeColors = diffProductInfo.getColors();
        final List<Size> removeSizes = diffProductInfo.getSizes();
        for (ProductInfo findProductInfo : findProductInfos) {
            if (removeColors.contains(findProductInfo.getColor())
                    || removeSizes.contains(findProductInfo.getSize())) {
                findProductInfo.setDeleteYn(DeleteYn.Y);
            }
        }
    }

    // 컬러와 사이즈 추가
    private void addColorAndSize(final List<ProductInfo> findProductInfos, final DiffProductInfo expectAddColorAndSize) {
        final List<Color> addColors = expectAddColorAndSize.getColors();
        final List<Size> addSizes = expectAddColorAndSize.getSizes();
        final List<ProductInfo> deletedProductInfos = findProductInfos.stream().filter(productInfo -> {
            return productInfo.getDeleteYn() == DeleteYn.Y;
        }).collect(Collectors.toList());
        List<ProductInfo> addProductInfos = new ArrayList<>();

        Product product = findProductInfos.get(0).getProduct();

        for (Color addColor : addColors) {
            for (Size addSize : addSizes) {
                final Optional<ProductInfo> findProductInfo = findProductInfos.stream().filter(productInfo -> {
                    return productInfo.getColor() == addColor && productInfo.getSize() == addSize;
                }).findFirst();

                if (findProductInfo.isEmpty()) {
                    addProductInfos.add(new ProductInfo(product, addSize, addColor, -1));
                    continue;
                }

                findProductInfo.get().setDeleteYn(DeleteYn.N);
            }
        }
        productInfoService.saveAll(addProductInfos);
    }


    @Getter @Setter
    static class DiffProductInfo {
        private List<Color> colors;
        private List<Size> sizes;

        public DiffProductInfo(final List<Color> colors, final List<Size> sizes) {
            this.colors = colors;
            this.sizes = sizes;
        }
    }
}
