package com.zibal.service.PaymentOrder;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.function.EntityResponse;

@Service
public class OrderStartPaymentService {
    private String startUrl = "https://gateway.zibal.ir/start/";
    public ResponseEntity<String> startOrderPayment(Long trackId) {
        try {
            startUrl=startUrl+trackId;
            var restClient = RestClient.create();
            EntityResponse<?> response = restClient.get()
                    .uri(startUrl)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {}); // to deserialization jason response

            if (response != null) {
                return new ResponseEntity<String>("start payment successfully.", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>( "We could not get any response.", HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            return new ResponseEntity<String>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
