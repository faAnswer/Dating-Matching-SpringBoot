package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_user_info")
public class UserInfoEntity {

    @Id
    private int uid;

    @Column(name = "gender")
    private String gender;

    @Column(name = "picid")
    private String picId;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "sexual")
    private String sexual;

    @Column(name = "districtid")
    private Integer districtid;

    @Column(name = "age")
    private Integer age;

}