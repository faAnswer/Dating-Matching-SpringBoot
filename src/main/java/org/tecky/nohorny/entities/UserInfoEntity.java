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
    private int gender;

    @Column(name = "picid")
    private String picId;

    @Column(name = "sexal")
    private int sexal;

    @Column(name = "districtid")
    private int districtId;

    @Column(name = "age")
    private int age;

}