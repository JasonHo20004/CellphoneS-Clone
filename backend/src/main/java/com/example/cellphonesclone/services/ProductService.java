package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.ProductDTO;
import com.example.cellphonesclone.DTO.ProductImageDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.exceptions.InvalidParamException;
import com.example.cellphonesclone.models.Brand;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.models.ProductImage;
import com.example.cellphonesclone.responses.ProductResponse;
import com.example.cellphonesclone.respositories.BrandRespository;
import com.example.cellphonesclone.respositories.ProductImageRepository;
import com.example.cellphonesclone.respositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final BrandRespository brandRespository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Brand existingBrand = brandRespository.findById(productDTO.getBrandId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find Brand with ID: "+productDTO.getBrandId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .brand(existingBrand)
                .color(productDTO.getColor())
                .rom(productDTO.getRom())
                .ram(productDTO.getRam())
                .batteryCapacity(productDTO.getBatteryCapacity())
                .frontCamera(productDTO.getFrontCamera())
                .mainCamera(productDTO.getMainCamera())
                .screenSize(productDTO.getScreenSize())
                .description(productDTO.getDescription())
                .operatingSystem(productDTO.getOperatingSystem())
                .inStock(productDTO.getInStock())
                .releaseDate(productDTO.getReleaseDate())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductByID(long productID) throws Exception {
        Optional<Product> optionalProduct = productRepository.getDetailProduct(productID);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + productID);
    }

    @Override
    public Page<ProductResponse> getAllProduct(String keyword, Long brandId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và brandId (nếu có)
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(brandId, keyword, pageRequest);
        return productsPage.map(ProductResponse::fromProduct);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

    @Override
    @Transactional
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductByID(id);
        if(existingProduct != null){
            Brand existingBrand = brandRespository.findById(productDTO.getBrandId())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find Brand with ID: "+productDTO.getBrandId()));

            //ModelMapper de copy thuoc tinh tu DTO -> Product
            existingProduct.setName(productDTO.getName());
            existingProduct.setBrand(existingBrand);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setOperatingSystem(productDTO.getOperatingSystem());
            existingProduct.setRam(productDTO.getRam());
            existingProduct.setRom(productDTO.getRom());
            existingProduct.setColor(productDTO.getColor());
            existingProduct.setBatteryCapacity(productDTO.getBatteryCapacity());
            existingProduct.setMainCamera(productDTO.getMainCamera());
            existingProduct.setFrontCamera(productDTO.getFrontCamera());
            existingProduct.setReleaseDate(productDTO.getReleaseDate());
            existingProduct.setInStock(productDTO.getInStock());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    @Transactional
    public ProductImage createProductImage(Long productID, ProductImageDTO productImageDTO)
            throws Exception {
        Product existingProduct = productRepository.findById(productID)
                .orElseThrow(
                        ()-> new DataNotFoundException("Cannot find product with ID: "+productImageDTO.getProductId())
                );
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho insert qua 5 anh cho 1 san pham
        int size = productImageRepository.findByProductProductId(productID).size();
        if(size>=ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of images must be <= "+ ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
