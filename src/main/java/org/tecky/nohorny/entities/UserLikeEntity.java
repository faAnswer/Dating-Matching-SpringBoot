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
@Table(name = "t_user_like")
@IdClass(org.tecky.nohorny.entities.comprimarykey.UserLikeEntityPK.class)
public class UserLikeEntity {

    @Id
    private int touid;

    @Id
    private int fromuid;

}