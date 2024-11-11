package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.OrderDetailDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.OrderDetail;

import java.util.List;

public interface IOderDetailService {
    OrderDetail createOrderDetail (OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData);
    void deleteOrderDetail(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
