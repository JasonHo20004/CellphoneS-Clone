package com.example.cellphonesclone.respositories;

import com.example.cellphonesclone.models.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderID(Long orderID);
}
