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
@Table(name = "t_user")
public class UserEntity {

    @Id
    private int uid;

    @Column(name = "salt")
    private String salt;

    @Column(name = "shapassword")
    private String shapassword;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

}