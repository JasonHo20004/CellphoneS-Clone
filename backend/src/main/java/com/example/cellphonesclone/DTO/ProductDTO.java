package com.example.cellphonesclone.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 100000000, message = "Price must be less than or equal to 100,000,000")
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

