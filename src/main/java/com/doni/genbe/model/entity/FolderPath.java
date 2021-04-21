package com.doni.genbe.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_path")
public class FolderPath {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pathid")
    private Long id;

    @Column(name = "new_path")
    private String newPath;

    @Column(name = "finish_path")
    private String finishPath;

    @Column(name = "upload_path")
    private String uploadPath;
}
