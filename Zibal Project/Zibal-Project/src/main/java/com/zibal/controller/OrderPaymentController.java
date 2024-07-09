package com.zibal.controller;

import com.zibal.repository.OrderRepository;
import com.zibal.service.PaymentOrder.OrderStartPaymentService;
import com.zibal.service.PaymentOrder.OrderUpdateService;
import com.zibal.service.PaymentOrder.OrderVerifyPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.zibal.service.PaymentOrder.OrederRequestService;

@RestController
@RequestMapping(path="/orderPayment")
public class OrderPaymentController {
    @Autowired
    private OrederRequestService orederRequestService;
    @Autowired
    private OrderStartPaymentService orderStartPaymentService;
    @Autowired
    private OrderUpdateService orderUpdateService;
    @Autowired
    private OrderVerifyPayment orderVerifyPayment;
    @Autowired
    OrderRepository orderRepository;
    private Long trackId;

    @PostMapping("/requestPayment/{orderId}")
    public ResponseEntity<String> requestPayment(@PathVariable Integer orderId, @RequestParam Long amount) {
        ResponseEntity<String> responseEntity = orederRequestService.createPaymentRequest(orderId, amount);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String result = responseEntity.getBody();
            if (result != null) {
                result = result.split(" ")[1];
                trackId = Long.parseLong(result);

            }
        }
        return responseEntity;
    }
    @GetMapping(path="/startPayment")
    public ResponseEntity<String> startOrderPayment(@RequestParam Long trackId)
    {
        return orderStartPaymentService.startOrderPayment(trackId);
    }
    @GetMapping("/callBack")
    public ResponseEntity<String> handleCallback(@RequestParam("success") int success,
            @RequestParam("trackId") Long trackId, @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam("status") int status)
    {
        return orderUpdateService.updateOrderStatus(orderId,success,status);

    }
    @GetMapping("/verifyPayment")
    public ResponseEntity<String> verifyPayment(@RequestParam Long trackId){
        return orderVerifyPayment.verifyPayment(trackId);
    }

}
