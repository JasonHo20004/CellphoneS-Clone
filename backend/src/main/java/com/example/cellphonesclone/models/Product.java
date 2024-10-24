package com.example.cellphonesclone.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name", length = 350, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "operating_system", length = 255)
    private String operatingSystem;

    @Column(name = "screen_size", precision = 5, scale = 2)
    private BigDecimal screenSize;

    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Column(name = "ram")
    private Integer ram;

    @Column(name = "rom")
    private Integer rom;

    @Column(name = "front_camera", length = 350)
    private String frontCamera;

    @Column(name = "main_camera", length = 350)
    private String mainCamera;

    @Column(name = "color", length = 255)
    private String color;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @ManyToOne
    @JoinColumn(name = "brands_id")
    private Brand brand;
}
