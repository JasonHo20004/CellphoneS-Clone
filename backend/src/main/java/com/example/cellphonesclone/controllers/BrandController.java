package com.example.cellphonesclone.controllers;

import com.example.cellphonesclone.DTO.BrandDTO;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.services.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/brands")
//@Validated
//Dependency Injection
@RequiredArgsConstructor

public class BrandController {

    private final BrandService brandService;

    //Data Transfer Object = Request Object
    @PostMapping("")
    public ResponseEntity<?>createBrands(@Valid @RequestBody BrandDTO brandDTO, BindingResult result)
    {
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        brandService.createBrand(brandDTO);
        return ResponseEntity.ok("Insert Brand successfully");
    }

    @GetMapping("") //http://localhost:8080/api/v1/brands?page=1&limit=10
    public ResponseEntity<List<Brand>> getAllBrands(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
        )
    {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>updateBrands(@PathVariable Long id,@Valid @RequestBody BrandDTO brandDTO){
        brandService.updateBrand(id, brandDTO);
        return ResponseEntity.ok("Update brand successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteBrands(@PathVariable Long id){
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Delete brand with id " + id+" successfully.");
    }
    
}
