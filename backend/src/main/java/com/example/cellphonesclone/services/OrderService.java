package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.OrderDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Order;
import com.example.cellphonesclone.models.OrderStatus;
import com.example.cellphonesclone.models.User;
import com.example.cellphonesclone.respositories.OrderRepository;
import com.example.cellphonesclone.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        //kiem tra userId co ton tai ko
        User user = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(()->new DataNotFoundException("Cannot find user with id: "+ orderDTO.getUserID()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper->mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date()); //thoi diem hien tai
        order.setStatus(OrderStatus.PENDING); //mac dinh pending

        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now(): orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return List.of();
    }
}
