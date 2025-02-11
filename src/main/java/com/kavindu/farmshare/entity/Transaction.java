package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "price",nullable = false)
    private double price;

    @Column(name = "time",nullable = false)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "from_user_id",nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id",nullable = false)
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id",nullable = false)
    private TransactionType transactionType;

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
