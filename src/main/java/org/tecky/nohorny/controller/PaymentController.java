package org.tecky.nohorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tecky.nohorny.entities.OrderEntity;
import org.tecky.nohorny.services.intf.IPaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    IPaymentService iPaymentService;


    @GetMapping("/core")
    public ResponseEntity<?> paidCore(@RequestParam Map<String, String> paymentInfo, Authentication authentication){

        Map<String, String> map = new HashMap<>();

        map.put("item", "test");
        map.put("unitrate", "100");
        map.put("qty", "100");

        OrderEntity orderEntity = iPaymentService.getOrder(map, authentication);

        return iPaymentService.getPayment(orderEntity, authentication);
    }
}
