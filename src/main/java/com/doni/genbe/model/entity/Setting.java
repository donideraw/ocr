package com.doni.genbe.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_setting")
public class Setting {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "setid")
    private Long id;

    private String key;
    private String value;
    private String keterangan;
}
