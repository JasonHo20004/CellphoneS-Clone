package com.example.cellphonesclone.controllers;
import com.example.cellphonesclone.DTO.ProductDTO;
import com.example.cellphonesclone.DTO.ProductImageDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.models.ProductImage;
import com.example.cellphonesclone.responses.ProductListResponse;
import com.example.cellphonesclone.responses.ProductResponse;
import com.example.cellphonesclone.respositories.BrandRespository;
import com.example.cellphonesclone.respositories.ProductRepository;
import com.example.cellphonesclone.services.IProductService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final BrandRespository brandRespository;

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
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProduct(pageRequest);

        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(products)
                        .totalPages(totalPages)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByID(@PathVariable("id") Long productID){
        try {
            Product existingProduct = productService.getProductByID(productID);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO){
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //@PostMapping("/generateFakeProducts")
    private ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();

        List<String> phoneModels = Arrays.asList(
                // iOS Models
                "iPhone 14", "iPhone 14 Pro", "iPhone 14 Pro Max", "iPhone 13",
                "iPhone 13 Mini", "iPhone 13 Pro", "iPhone 13 Pro Max", "iPhone 12",
                "iPhone 12 Mini", "iPhone 12 Pro", "iPhone 12 Pro Max", "iPhone SE (3rd generation)",
                "iPhone 11", "iPhone 11 Pro", "iPhone 11 Pro Max", "iPhone XR",
                "iPhone XS", "iPhone XS Max", "iPhone X", "iPhone 8",
                "iPhone 8 Plus", "iPhone 7", "iPhone 7 Plus",

                // Android Models
                "Samsung Galaxy S23", "Samsung Galaxy S23 Ultra", "Samsung Galaxy Z Fold 4",
                "Samsung Galaxy Z Flip 4", "Google Pixel 7", "Google Pixel 7 Pro",
                "OnePlus 11", "OnePlus Nord 2", "Xiaomi 13 Pro", "Xiaomi 12T",
                "Oppo Find N", "Oppo Reno8 Pro", "Motorola Edge 30", "Motorola Razr 5G",
                "Sony Xperia 1 IV", "Sony Xperia 10 IV", "Realme GT 2 Pro", "Vivo X80",
                "ASUS ROG Phone 6", "Nokia X30 5G", "HTC Desire 22 Pro",
                "Honor Magic 4 Pro", "Huawei P50 Pro", "ZTE Axon 20",
                "LG Velvet", "Lava Agni 2"
        );


        for (int i = 0;i < phoneModels.size(); i++){
            String productName = faker.options().option(phoneModels.toArray(new String[0]));  // Convert List to Array
            if(productService.existsByName(productName)){
                continue;
            }

            Long brandId = (long) faker.number().numberBetween(2, 5);
            Brand existingBrand = brandRespository.findById(brandId)
                    .orElse(null);  // Handle missing brands

            if (existingBrand == null) {
                continue;  // Skip if brand is not found
            }

            // Set operating system based on brand
            String operatingSystem;
            if ("Apple".equalsIgnoreCase(existingBrand.getName())) {
                operatingSystem = "iOS";
            } else {
                operatingSystem = "Android";
            }

            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(1_000_000, 30_000_000))
                    .description(faker.lorem().sentence())
                    .brandId((long)faker.number().numberBetween(2,5))
                    .color(faker.color().name())  // Generates a random color name
                    .rom(faker.options().option(64, 128, 256, 512))  // ROM size options
                    .ram(faker.options().option(4, 6, 8, 12))  // RAM size options
                    .batteryCapacity(faker.number().numberBetween(3000, 5000))  // Battery capacity in mAh
                    .frontCamera(faker.number().numberBetween(8, 32) + "MP")  // Front camera resolution
                    .mainCamera(faker.number().numberBetween(12, 108) + "MP")  // Main camera resolution
                    .thumbnail("")
                    .screenSize(BigDecimal.valueOf(faker.number().randomDouble(1,5,7)))  // Screen size
                    .description(faker.lorem().sentence())  // Random product description
                    .operatingSystem(operatingSystem)  // Random OS
                    .inStock(faker.number().numberBetween(100, 1_000))  // Boolean value for stock status
                    .releaseDate(faker.number().numberBetween(1996, LocalDate.now().getYear()))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products created successfully!");
    }
}
