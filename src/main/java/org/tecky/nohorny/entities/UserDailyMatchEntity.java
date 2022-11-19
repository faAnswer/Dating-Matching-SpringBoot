package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "t_user_daily_match")
public class UserDailyMatchEntity {

    @Id
    private int uid;

    @Column(name = "ismatch")
    private int ismatch;
}