package com.zibal.service.PaymentOrder;

import com.zibal.entity.Orders;
import com.zibal.orederClass.VerifyRequest;
import com.zibal.orederClass.VerifyResult;
import com.zibal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OrderVerifyPayment {
    @Autowired
    private OrderRepository orderRepository;
    private final String ZIBAL_VERIFY_URL = "https://gateway.zibal.ir/v1/verify";
    private final String MERCHANT = "zibal";

    public Orders findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> verifyPayment(Long trackId) {
        VerifyRequest request = new VerifyRequest(trackId, MERCHANT);
        try {
            var restClient = RestClient.create();
            ResponseEntity<VerifyResult> response = restClient.post()
                    .uri(ZIBAL_VERIFY_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toEntity(VerifyResult.class);

            VerifyResult resultOfVerification = response.getBody();
            if (resultOfVerification != null) {
                Orders order = findOrderById(Integer.parseInt(resultOfVerification.getOrderId()));
                if (order == null) {
                    throw new RuntimeException("Order not found");
                }
                if (resultOfVerification.getStatus() == 1) {
                    order.setDate(resultOfVerification.getPaidAt());
                    orderRepository.save(order);
                    new ResponseEntity<>("Payment is valid", HttpStatus.ACCEPTED);
                } else {
                    new ResponseEntity<>("Not valid payment check the status code: " + resultOfVerification.getStatus(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("result of request is null", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}