package org.tecky.nohorny.entities.comprimarykey;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserRoleEntityPK implements Serializable {

    private int uid;
    private int roleid;
}
