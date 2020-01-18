package com.example.study1.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long fileSize;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
