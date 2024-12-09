package com.example.cellphonesclone.controllers;
import com.example.cellphonesclone.DTO.ProductDTO;
import com.example.cellphonesclone.DTO.ProductImageDTO;
import com.example.cellphonesclone.components.LocalizationUtils;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.models.ProductImage;
import com.example.cellphonesclone.responses.ProductListResponse;
import com.example.cellphonesclone.responses.ProductResponse;
import com.example.cellphonesclone.respositories.BrandRespository;
import com.example.cellphonesclone.respositories.ProductRepository;
import com.example.cellphonesclone.services.IProductService;
import com.example.cellphonesclone.utils.MessageKeys;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
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
    private final LocalizationUtils localizationUtils;
    private final BrandRespository brandRespository;

    @PostMapping("")
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ){
        try {
            Product existingProduct = productService.getProductByID(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(localizationUtils
                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_5));
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body(localizationUtils
                                    .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE));
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getProductId(),
                        ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "brand_id") Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<ProductResponse> productPage = productService.getAllProduct(keyword, brandId, pageRequest);
        // Lấy tổng số trang
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }
    //http://localhost:8088/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable("id") Long productId
    ) {
        try {
            Product existingProduct = productService.getProductByID(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductsByIds(@RequestParam("ids") String ids) {
        //eg: 1,3,5,7
        try {
            // Tách chuỗi ids thành một mảng các số nguyên
            List<Long> productIds = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Product> products = productService.findProductsByIds(productIds);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //update a product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDTO productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
