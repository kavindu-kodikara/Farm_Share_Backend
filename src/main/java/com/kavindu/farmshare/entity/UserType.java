package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_type")
public class UserType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type",nullable = false)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    public UserType() {
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
