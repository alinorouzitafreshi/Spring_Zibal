package com.zibal.service.Order;

import com.zibal.entity.Orders;
import com.zibal.enums.PaymentStatus;
import com.zibal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Date;
@Service
public class UpdateOrderService {
  @Autowired
  OrderRepository orderRepository;
    public Orders findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
    public ResponseEntity<?> updateOrderInfo( int id, Long amount, Date date,  PaymentStatus paymentStatus){
        Orders existingOrder = findOrderById(id);
       existingOrder.setAmount(amount);
       existingOrder.setStatus(paymentStatus);
       existingOrder.setDate(date);
       orderRepository.save(existingOrder);
        return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
    }
}
