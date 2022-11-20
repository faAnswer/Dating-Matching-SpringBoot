package org.tecky.nohorny.services.intf;

import org.springframework.security.core.Authentication;
import org.tecky.nohorny.entities.OrderEntity;
import org.tecky.nohorny.entities.PaymentEntity;

import java.util.Map;

public interface IPaymentService {

    public OrderEntity getOrder(Map<String, String> paymentInfo, Authentication authentication);

    public PaymentEntity getPayment(OrderEntity orderEntity, Authentication authentication);

}
