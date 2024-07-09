package com.zibal.controller;


import com.zibal.entity.Orders;
import com.zibal.enums.PaymentStatus;
import com.zibal.repository.OrderRepository;
import com.zibal.service.Order.AddOrderService;
import com.zibal.service.Order.DeleteOrderService;
import com.zibal.service.Order.UpdateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path="/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AddOrderService addOrderService;
    @Autowired
    UpdateOrderService updateOrderService;
    @Autowired
    DeleteOrderService deleteOrderService;

    @PostMapping(path="/addOrder")
    public @ResponseBody String addNewUser (@RequestParam Long amount) {

        return addOrderService.addOrder(amount);
    }

    @GetMapping(path="/getAllOrders")
    public @ResponseBody Iterable<Orders> getAllUsers() {

        return orderRepository.findAll();
    }

    @PutMapping(path="/updateOrder")
    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestParam Long amount, @RequestParam Date date,@RequestParam PaymentStatus paymentStatus) {
       return updateOrderService.updateOrderInfo(id,amount,date,paymentStatus);
    }
    @DeleteMapping(path="/deleteOrder")
    public ResponseEntity<?> deleteUser(@RequestParam int id) {
        return deleteOrderService.deleteOrder(id);
    }
}
