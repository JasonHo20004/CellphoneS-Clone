package com.example.cellphonesclone.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{

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

    @JsonProperty("brand_id")
    private Long brandId;
}
