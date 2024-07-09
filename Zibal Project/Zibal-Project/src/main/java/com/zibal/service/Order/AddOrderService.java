package com.zibal.service.Order;


import com.zibal.entity.Orders;
import com.zibal.enums.PaymentStatus;
import com.zibal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddOrderService {
    @Autowired
    private OrderRepository orderRepository;
    public String addOrder(Long amount){
        Orders order=new Orders();
        order.setStatus(PaymentStatus.NOT_PAYED);
        order.setAmount(amount);
        orderRepository.save(order);
        return "Saved";
    }
}
