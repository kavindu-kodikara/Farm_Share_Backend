package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "soil_score")
public class SoilScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "score",nullable = false)
    private int score;

    @Column(name = "date",nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "soil_doc_id",nullable = false)
    private SoilDoc soilDoc;

    public SoilScore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SoilDoc getSoilDoc() {
        return soilDoc;
    }

    public void setSoilDoc(SoilDoc soilDoc) {
        this.soilDoc = soilDoc;
    }
}
