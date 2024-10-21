package com.example.cellphonesclone.controllers;

import com.example.cellphonesclone.DTO.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("create order detail here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("get order detail with id" +id);
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderID") Long orderID){
        //List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderID);
        return ResponseEntity.ok("get order details with orderID ="+orderID);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @RequestBody OrderDetailDTO newOrderDetailData){
        return ResponseEntity.ok("update order detail with id = "+ id+", new order detail data:"+newOrderDetailData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.noContent().build();
    }
}
