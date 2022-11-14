package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "t_user_role")
@IdClass(org.tecky.nohorny.entities.comprimarykey.UserRoleEntityPK.class)
public class UserRoleEntity {

    @Id
    private int uid;

    @Id
    private int roleid;

}