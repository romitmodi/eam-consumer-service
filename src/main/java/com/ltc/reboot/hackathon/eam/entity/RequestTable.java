package com.ltc.reboot.hackathon.eam.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table
@Builder
public class RequestTable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String status;

    @Column
    String JiraId;
}
