package org.tecky.nohorny.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserProfileDTO {

    private int uid;
    private String gender;
    private String avatarUrl;
    private String hobbies;
    private String sexual;
    private int districtid;
    private int age;

}