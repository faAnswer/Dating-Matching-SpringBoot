package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "t_user_contact")
@IdClass(org.tecky.nohorny.entities.comprimarykey.UserContactEntityPK.class)
public class UserContactEntity {

    @Id
    private int uid;

    @Id
    private int contactuid;

    @Column(name = "statuscode")
    private int statuscode;

}