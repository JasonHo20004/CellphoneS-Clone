package com.example.cellphonesclone.services;
import com.example.cellphonesclone.DTO.ProductImageDTO;
import  com.example.cellphonesclone.models.*;
import com.example.cellphonesclone.DTO.ProductDTO;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductByID(long id) throws Exception;
    Page<ProductResponse> getAllProduct(String keyword,
                                        Long brandId, PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(Long productID, ProductImageDTO productImageDTO)
            throws Exception;
    List<Product> findProductsByIds(List<Long> productIds);
}
