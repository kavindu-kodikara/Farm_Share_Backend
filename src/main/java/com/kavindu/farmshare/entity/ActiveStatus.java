package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "active_status")
public class ActiveStatus implements Serializable {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    public ActiveStatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
