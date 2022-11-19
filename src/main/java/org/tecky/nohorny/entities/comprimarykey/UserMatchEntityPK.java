package org.tecky.nohorny.entities.comprimarykey;

import lombok.Data;
import java.io.Serializable;


@Data
public class UserMatchEntityPK implements Serializable {

    private int uid;
    private int matchuid;
}