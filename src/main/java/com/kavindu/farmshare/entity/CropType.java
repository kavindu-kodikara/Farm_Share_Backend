package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "crop_type")
public class CropType implements Serializable {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    public CropType() {
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
