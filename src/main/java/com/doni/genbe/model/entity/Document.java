package com.doni.genbe.model.entity;

import com.doni.genbe.helper.SuccessType;

import javax.persistence.*;
import lombok.Data;
import org.w3c.dom.Text;

@Data
@Entity
@Table(name = "t_document")
public class Document {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "docid")
    private Long id;
    @Column(name = "docname")
    private String docName;
    @Column (name = "start_path")
    private String startingPath;
    @Column (name = "finish_path")
    private String finishingPath;
    @Column (name = "is_scanned")
    private boolean isScanned;
    @Column (name = "is_sent")
    private boolean isSent = false;
    private SuccessType success;
    private String result;
}
