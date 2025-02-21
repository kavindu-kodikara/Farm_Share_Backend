package com.kavindu.farmshare.dto;

import com.kavindu.farmshare.entity.User;

import java.io.Serializable;
import java.util.List;

public class AdminUserDto implements Serializable {
    private List<User> farmerList;
    private List<User> investorList;
    private boolean success;

    public AdminUserDto() {
    }

    public List<User> getFarmerList() {
        return farmerList;
    }

    public void setFarmerList(List<User> farmerList) {
        this.farmerList = farmerList;
    }

    public List<User> getInvestorList() {
        return investorList;
    }

    public void setInvestorList(List<User> investorList) {
        this.investorList = investorList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
