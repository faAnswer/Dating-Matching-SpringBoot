package org.tecky.nohorny.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserProfileDTO {

    private Integer uid;
    private String gender;
    private String avatarUrl;
    private String hobbies;
    private String sexual;
    private Integer districtid;
    private Integer age;

}