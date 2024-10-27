package com.example.cellphonesclone.respositories;

import com.example.cellphonesclone.models.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);//phân trang
}
