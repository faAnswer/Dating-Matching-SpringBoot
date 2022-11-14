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
@Table(name = "t_pm_content")
public class PmContentEntity {

    @Id
    private int pmid;

    @Column(name = "content")
    private String content;

}