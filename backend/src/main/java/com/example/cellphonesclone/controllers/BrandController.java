package com.example.cellphonesclone.controllers;

import com.example.cellphonesclone.components.LocalizationUtils;
import com.example.cellphonesclone.DTO.*;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.responses.BrandResponse;
import com.example.cellphonesclone.responses.UpdateBrandResponse;
import com.example.cellphonesclone.services.BrandService;
import com.example.cellphonesclone.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/brands")
//@Validated
//Dependency Injection
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final LocaleResolver localeResolver;
    private final MessageSource messageSource;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    //Nếu tham số truyền vào là 1 object thì sao ? => Data Transfer Object = Request Object
    public ResponseEntity<BrandResponse> createBrand(
            @Valid @RequestBody BrandDTO brandDTO,
            BindingResult result) {
        BrandResponse brandResponse = new BrandResponse();
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            brandResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            brandResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(brandResponse);
        }
        Brand brand = brandService.createBrand(brandDTO);
        brandResponse.setBrand(brand);
        return ResponseEntity.ok(brandResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<Brand>> getAllBrands(
            @RequestParam("page")     int page,
            @RequestParam("limit")    int limit
    ) {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateBrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandDTO brandDTO
    ) {
        UpdateBrandResponse updateBrandResponse = new UpdateBrandResponse();
        brandService.updateBrand(id, brandDTO);
        updateBrandResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_BRAND_SUCCESSFULLY));
        return ResponseEntity.ok(updateBrandResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_BRAND_SUCCESSFULLY));
    }
}