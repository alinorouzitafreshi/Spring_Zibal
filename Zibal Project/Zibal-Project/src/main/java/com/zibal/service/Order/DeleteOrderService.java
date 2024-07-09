package com.zibal.service.Order;

import com.zibal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderService {
    @Autowired
    OrderRepository orderRepository;

    public ResponseEntity<?> deleteOrder (int id) {
        if (!orderRepository.existsById(id)) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        orderRepository.deleteById(id);
        return new ResponseEntity<>("Order with id = " + id + " deleted", HttpStatus.OK);
    }
}
