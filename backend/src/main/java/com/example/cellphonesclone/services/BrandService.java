package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.BrandDTO;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.respositories.BrandRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRespository brandRespository;

    @Override
    public Brand createBrand(BrandDTO brandDTO) {
        Brand newBrand = Brand.builder().name(brandDTO.getName()).build();
        return brandRespository.save(newBrand);
    }

    @Override
    public Brand getBrandByID(long id) {
        return brandRespository.findById(id).orElseThrow(()-> new RuntimeException("Brand not found."));
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRespository.findAll();
    }

    @Override
    public Brand updateBrand(Long brandID, BrandDTO brandDTO) {
        Brand existingBrand = getBrandByID(brandID);
        existingBrand.setName(brandDTO.getName());
        brandRespository.save(existingBrand);
        return existingBrand;
    }

    @Override
    public void deleteBrand(Long id) {
        brandRespository.deleteById(id);
    }
}
