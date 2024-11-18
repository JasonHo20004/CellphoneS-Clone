package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.BrandDTO;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.respositories.BrandRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRespository brandRepository;
    @Override
    @Transactional
    public Brand createBrand(BrandDTO BrandDTO) {
        Brand newBrand = Brand
                .builder()
                .name(BrandDTO.getName())
                .build();
        return brandRepository.save(newBrand);
    }

    @Override
    public Brand getBrandByID(long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand updateBrand(Long brandID, BrandDTO brand) {
        Brand existingBrand = getBrandByID(brandID);
        existingBrand.setName(brand.getName());
        brandRepository.save(existingBrand);
        return existingBrand;
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
