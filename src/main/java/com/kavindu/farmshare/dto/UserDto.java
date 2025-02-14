package com.kavindu.farmshare.dto;

import com.kavindu.farmshare.entity.ActiveStatus;
import com.kavindu.farmshare.entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {

    private int id;
    private String mobile;
    private String fname;
    private String lname;
    private String password;
    private String rePassword;
    private Date date;
    private String profilePic;
    private String userType;
    private String activeStatus;

    public UserDto() {
    }

    public UserDto(int id,String mobile, String fname, String lname, String password, Date date, String profilePic, String userType, String activeStatus) {
        this.mobile = mobile;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.date = date;
        this.profilePic = profilePic;
        this.userType = userType;
        this.activeStatus = activeStatus;
        this.id = id;
    }

    public String getRePassword() {
        return rePassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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
}
