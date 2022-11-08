package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_user_login")
public class UserLoginEntity {

    @Id
    private int uid;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

}