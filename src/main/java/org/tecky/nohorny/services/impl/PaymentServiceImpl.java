package org.tecky.nohorny.services.impl;

import org.faAnswer.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.OrderEntity;
import org.tecky.nohorny.entities.PaymentEntity;
import org.tecky.nohorny.mapper.OrderEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.services.intf.IPaymentService;

import java.util.Map;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Value("${jwt.secret}")
    private String clientSecret;

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Autowired
    OrderEntityRepository orderEntityRepository;

    @Override
    public OrderEntity getOrder(Map<String, String> paymentInfo, Authentication authentication) {

        int uid = userEntityRespostity.findByUsername(authentication.getName()).getUid();

        String item = paymentInfo.get("item");
        Integer unitrate = Integer.valueOf(paymentInfo.get("unitrate"));
        Integer qty = Integer.valueOf(paymentInfo.get("qty"));
        Integer totalsum = unitrate * qty;

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUid(uid);
        orderEntity.setItem(item);
        orderEntity.setUnitrate(unitrate);
        orderEntity.setQty(qty);
        orderEntity.setTotalsum(totalsum);

        orderEntityRepository.saveAndFlush(orderEntity);

        return orderEntity;
    }

    @Override
    public PaymentEntity getPayment(OrderEntity orderEntity, Authentication authentication) {

        JwtToken paymentJWT = new JwtToken(clientSecret);

        paymentJWT
                .setPayload("action", "payment")
                .setPayload("client_id", "nohorny")
                .setPayload("totalsum", orderEntity.getTotalsum())
                .setPayload("orderid", orderEntity.getUid());







        JwtToken





        return null;
    }
}
