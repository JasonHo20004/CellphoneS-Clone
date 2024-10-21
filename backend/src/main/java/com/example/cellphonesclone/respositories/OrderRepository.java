package com.example.cellphonesclone.respositories;

import com.example.cellphonesclone.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserID(Long userID);
}
