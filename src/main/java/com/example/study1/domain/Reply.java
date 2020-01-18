package com.example.study1.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;


}
