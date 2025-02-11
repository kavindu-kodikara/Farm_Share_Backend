package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "documents")
public class Documents implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nic_front",nullable = false)
    private String nicFront;

    @Column(name = "nic_back",nullable = false)
    private String nicBack;

    @Column(name = "ownership_doc",nullable = false)
    private String ownershipDoc;

    @Column(name = "analysis_doc",nullable = false)
    private String analysisDoc;

    public Documents() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNicFront() {
        return nicFront;
    }

    public void setNicFront(String nicFront) {
        this.nicFront = nicFront;
    }

    public String getNicBack() {
        return nicBack;
    }

    public void setNicBack(String nicBack) {
        this.nicBack = nicBack;
    }

    public String getOwnershipDoc() {
        return ownershipDoc;
    }

    public void setOwnershipDoc(String ownershipDoc) {
        this.ownershipDoc = ownershipDoc;
    }

    public String getAnalysisDoc() {
        return analysisDoc;
    }

    public void setAnalysisDoc(String analysisDoc) {
        this.analysisDoc = analysisDoc;
    }
}
