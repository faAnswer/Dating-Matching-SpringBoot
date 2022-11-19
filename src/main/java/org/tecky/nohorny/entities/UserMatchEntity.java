package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_user_match")
@IdClass(org.tecky.nohorny.entities.comprimarykey.UserMatchEntityPK.class)
public class UserMatchEntity {

    @Id
    private int uid;

    @Id
    private int matchuid;
}