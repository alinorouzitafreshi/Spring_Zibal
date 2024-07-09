package com.zibal.service.PaymentOrder;

import com.zibal.entity.Orders;
import com.zibal.enums.PaymentStatus;
import com.zibal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderUpdateService {
    @Autowired
    private OrderRepository orderRepository;

    public Orders findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
    public ResponseEntity<String> updateOrderStatus(String orderId,int success,int status){
        int orderIdInteger=Integer.parseInt(orderId);
        Orders order=findOrderById(orderIdInteger);
        if (order==null){
            return new ResponseEntity<>("Dont have order with this id", HttpStatus.NOT_FOUND);
        }
        if (success==1){
            order.setStatus(PaymentStatus.PAYED);
            orderRepository.save(order);
            return new ResponseEntity<>("Payment successfully",HttpStatus.ACCEPTED);
        }
        order.setStatus(PaymentStatus.PAY_FAIL);
        orderRepository.save(order);
        return new ResponseEntity<>("Payment was failed.Check the status code to find out reason : "+status,HttpStatus.OK);
    }
}
