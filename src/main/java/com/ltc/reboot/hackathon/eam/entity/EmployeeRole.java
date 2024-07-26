package com.ltc.reboot.hackathon.eam.entity;


import com.ltc.reboot.hackathon.eam.dto.ERoleEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "emp_role")
@Getter
@Setter
@AllArgsConstructor
public class EmployeeRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERoleEmployee name;

    public EmployeeRole() {

    }

    public EmployeeRole(ERoleEmployee name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERoleEmployee getName() {
        return name;
    }

    public void setName(ERoleEmployee name) {
        this.name = name;
    }

}
