package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Getter
@Setter
@Table(name = "t_payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(name = "orderid")
    private int orderid;

    @Column(name = "paidmethod")
    private String paidmethod;

    @Column(name = "refid")
    private String refid;

}