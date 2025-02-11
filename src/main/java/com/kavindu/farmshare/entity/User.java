package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "mobile",nullable = false)
    private String mobile;

    @Column(name = "fname",nullable = false)
    private String fname;

    @Column(name = "lname",nullable = false)
    private String lname;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "dob",nullable = false)
    private Date date;

    @Column(name = "profile_pic",nullable = false)
    private String profilePic;

    @ManyToOne
    @JoinColumn(name = "user_type_id",nullable = false)
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "active_status_id",nullable = false)
    private ActiveStatus activeStatus;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }
}
