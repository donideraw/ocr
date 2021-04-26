package com.doni.genbe.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "t_scheduler")
public class Scheduler {
    @Id
    @Column(name = "schid")
    private String id;

    @Column(name = "is_running")
    private boolean isRunning;

    @Column(name = "running_at")
    private LocalTime runningAt;
}
