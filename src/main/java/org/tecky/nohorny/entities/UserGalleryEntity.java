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
@Table(name = "t_user_gallery")
public class UserGalleryEntity {

    @Id
    private int uid;

    @Column(name = "picid")
    private String picid;

}