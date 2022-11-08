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
@Table(name = "t_pm")
public class PmEntity {

    @Id
    private int pm_id;

    @Column(name = "to_uid")
    private int to_uid;

    @Column(name = "from_uid")
    private int from_uid;

}