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
    private int pmid;

    @Column(name = "touid")
    private int touid;

    @Column(name = "fromuid")
    private int fromuid;

    @Column(name = "uuid")
    private String uuid;
}