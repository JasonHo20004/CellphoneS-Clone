package com.example.cellphonesclone.responses;


import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    private Long id;

    private String name;

    @JsonProperty("price")
    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("operating_system")
    private String operatingSystem;

    @JsonProperty("screen_size")
    private BigDecimal screenSize;

    @JsonProperty("battery_capacity")
    private Integer batteryCapacity;

    private Integer ram;

    private Integer rom;

    @JsonProperty("front_camera")
    private String frontCamera;

    @JsonProperty("main_camera")
    private String mainCamera;

    private String color;

    @JsonProperty("release_date")
    private Integer releaseDate; // Năm phát hành

    @JsonProperty("in_stock")
    private Integer inStock;

    @JsonProperty("product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    @JsonProperty("brand_id")
    private Long brandId;

    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .brandId(product.getBrand().getId())
                .rom(product.getRom())
                .ram(product.getRam())
                .batteryCapacity(product.getBatteryCapacity())
                .color(product.getColor())
                .frontCamera(product.getFrontCamera())
                .mainCamera(product.getMainCamera())
                .operatingSystem(product.getOperatingSystem())
                .screenSize(product.getScreenSize())
                .inStock(product.getInStock())
                .releaseDate(product.getReleaseDate())
                .productImages(product.getProductImages())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
