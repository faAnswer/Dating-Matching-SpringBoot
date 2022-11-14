package org.tecky.nohorny.entities.comprimarykey;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLikeEntityPK implements Serializable {

    private int touid;
    private int fromuid;
}
