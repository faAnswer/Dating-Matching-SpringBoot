package org.tecky.nohorny.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "t_pm_status")
public class PmStatusEntity {

    @Id
    private int pmid;

    @Column(name = "isread")
    private int isread;

    @Column(name = "sendtime")
    private String sendtime;

    @Column(name = "readtime")
    private String readtime;
}