package com.example.cellphonesclone.controllers;

import com.example.cellphonesclone.DTO.BrandDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/brands")
//@Validated

public class BrandController {

    @GetMapping("") //http://localhost:8080/api/v1/brands?page=1&limit=10
    public ResponseEntity<String> getAllBrands(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
        )
    {
        return ResponseEntity.ok(String.format("getAllBrands, page = %d, limit = %d", page, limit));
    }
    //Data Transfer Object = Request Object
    @PostMapping("")
    public ResponseEntity<?>insertBrands(@Valid @RequestBody BrandDTO brandDTO, BindingResult result)
    {
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insert" + brandDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>updateBrands(@PathVariable Long id){
        return ResponseEntity.ok("Update brand with id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteBrands(@PathVariable Long id){
        return ResponseEntity.ok("Delete brand with id " + id);
    }
    
}
