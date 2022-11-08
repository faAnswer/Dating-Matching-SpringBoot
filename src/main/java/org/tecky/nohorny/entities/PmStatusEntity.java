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
@Table(name = "t_pm_status")
public class PmStatusEntity {

    @Id
    private int pm_id;

    @Column(name = "send_time")
    private String send_time;

    @Column(name = "from_alive")
    private int from_alive;

    @Column(name = "read_time")
    private String read_time;

    @Column(name = "to_read")
    private int to_read;

    @Column(name = "to_alive")
    private int to_alive;

    @Column(name = "replyto_id")
    private int replyto_id;

}