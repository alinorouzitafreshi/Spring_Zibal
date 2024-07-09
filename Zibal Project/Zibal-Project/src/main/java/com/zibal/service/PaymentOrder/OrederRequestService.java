package com.zibal.service.PaymentOrder;
import com.zibal.entity.Orders;
import com.zibal.orederClass.Request;
import com.zibal.orederClass.ResultOfRequest;
import com.zibal.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrederRequestService {
    @Autowired
    private OrderRepository orderRepository;
    private final String ZIBAL_REQUEST_URL = "https://gateway.zibal.ir/v1/request";
    private final String MERCHANT = "zibal";// replace with your actual merchant
    private final String CALLBACKURL="";
    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }

    public Orders findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
    public ResponseEntity<String> createPaymentRequest(Integer orderId, Long amount) {
        Orders order = findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        String strOrderId = orderId.toString();
        Request request=new Request(MERCHANT,amount,CALLBACKURL,strOrderId);
        try {
            var restClient = RestClient.create();
            ResponseEntity<ResultOfRequest> response = restClient.post()
                    .uri(ZIBAL_REQUEST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toEntity(ResultOfRequest.class);

            ResultOfRequest resultOfRequest=response.getBody();
            if(resultOfRequest != null)
            {
                long trackId= resultOfRequest.getTrackId();
                if(resultOfRequest.getResult()==100)
                {
                    new ResponseEntity<>("tackId: "+trackId, HttpStatus.ACCEPTED);
                }
                else
                {
                    new ResponseEntity<>("Check the status code: "+resultOfRequest.getResult(), HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("result of request is null", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
