package org.tecky.nohorny.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MatchPerson implements Serializable {

    private Integer uid;
    private String name;
    private String avatar;
}
