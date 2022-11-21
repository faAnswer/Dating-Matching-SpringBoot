package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "t_order")
public class OrderEntity {

    @Id
    private int orderid;

    @Column(name = "item")
    private String item;

    @Column(name = "uid")
    private int uid;

    @Column(name = "unitrate")
    private int unitrate;

    @Column(name = "qty")
    private int qty;

    @Column(name = "totalsum")
    private int totalsum;

    @Column(name = "ispaid")
    private int ispaid;

}