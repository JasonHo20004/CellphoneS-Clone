package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.BrandDTO;
import com.example.cellphonesclone.models.Brand;

import java.util.List;

public interface IBrandService {
    Brand createBrand(BrandDTO brand);
    Brand getBrandByID(long id);
    List<Brand> getAllBrands();
    Brand updateBrand(Long brandID, BrandDTO brand);
    void deleteBrand(Long id);
}
