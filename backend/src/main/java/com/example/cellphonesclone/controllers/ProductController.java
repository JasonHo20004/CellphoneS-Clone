package com.example.cellphonesclone.controllers;
import com.example.cellphonesclone.DTO.ProductDTO;
import com.example.cellphonesclone.DTO.ProductImageDTO;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.models.ProductImage;
import com.example.cellphonesclone.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            //@ModelAttribute("files") List<MultipartFile> files,
            //@RequestPart("file")MultipartFile file,
            BindingResult result
    ){
        try{
            if (result.hasErrors()) {
                String errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//             "name": "iPhone 15 Pro Max",
//            "price": 36000.000,
//            "thumbnail": "",
//            "description": "Latest iPhone model with advanced features",
//            "operating_system": "iOS 17",
//            "screen_size": 6.7,
//            "battery_capacity": 4323,
//            "ram": 8,
//            "rom": 256,
//            "front_camera": "12 MP",
//            "main_camera": "48 MP + 12 MP + 12 MP",
//            "color": "Silver",
//            "release_date": 2023,
//            "in_stock": 150,
//            "brand_id": 1

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long ProductID, @RequestParam("files") List<MultipartFile> files){
        try {
            Product existingProduct = productService.getProductByID(ProductID);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("You can upload 5 images at a time");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files){
                if (file.getSize()==0){
                    continue;
                }

                if (file.getSize()>10*1024*1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Your mom is fat! Maximum size is 10MB");
                }

                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image.");
                }

                String filename = storeFile(file);
                //Lưu vào đối tượng product trong DB
                ProductImage productImage = productService.createProductImage(existingProduct.getProductId(), ProductImageDTO.builder()
                        .imageUrl(filename)
                        .build());
                //lưu vào bảng product_images
                productImages.add(productImage);
            }
            return ResponseEntity.ok(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        if (!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Thêm UID vao truoc ten file de unique
        String uniqueFilename = UUID.randomUUID().toString() + '_' + filename;
        //Duong den thu muc
        Path uploadDir = Paths.get("upload");
        //Kiem tra va tao thu muc neu chua co
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Duong dan day du den file
        Path direction = Paths.get(uploadDir.toString(), uniqueFilename);
        //Sao chep file vao thu muc dich
        Files.copy(file.getInputStream(),direction, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("images/");
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        return ResponseEntity.ok("get products here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByID(@PathVariable("id") String productID){
        return ResponseEntity.ok("get product with ID: "  +productID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable long id){
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully.", id));
    }
}
