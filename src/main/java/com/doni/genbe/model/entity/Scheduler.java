package com.doni.genbe.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_scheduler")
public class Scheduler {
    @Id
    @Column(name = "schid")
    private String id;

    @Column(name = "is_running")
    private boolean isRunning;
}
