package com.example.cellphonesclone.respositories;

import com.example.cellphonesclone.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductProductId(Long productID);
}
