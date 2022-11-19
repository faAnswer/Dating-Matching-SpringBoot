package org.tecky.nohorny.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MatchPerson extends UserProfileDTO implements Serializable {

    private String username;

}
