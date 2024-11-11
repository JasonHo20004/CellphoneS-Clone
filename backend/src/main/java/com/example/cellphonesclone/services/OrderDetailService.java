package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.OrderDetailDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Order;
import com.example.cellphonesclone.models.OrderDetail;
import com.example.cellphonesclone.models.Product;
import com.example.cellphonesclone.respositories.OrderDetailRepository;
import com.example.cellphonesclone.respositories.OrderRepository;
import com.example.cellphonesclone.respositories.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDetailService implements IOderDetailService{
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //Tim xem order ID co ton tai kho?
        Order order = orderRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(()->new DataNotFoundException("Cannot find Order with id: "+orderDetailDTO.getOrderID()));
        Product product = productRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(()->new DataNotFoundException("Cannot find Product with id: "+orderDetailDTO.getOrderID()));
        OrderDetail orderDetail = new OrderDetail().builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Cannot find OrderDetail with id = "+id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData) {
        return null;
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
