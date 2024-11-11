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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
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
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //tim xem orderdetail co ton tai kh?
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Cannot find OrderDetail with id = "+id));
        //kiem tra orderi co thuoc ve order nao` do kh?
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderID()).orElseThrow(()->
                new DataNotFoundException("Cannot find Order with id = "+id));
        Product existingProduct = productRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(()->new DataNotFoundException("Cannot find Product with id: "+orderDetailDTO.getOrderID()));
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
