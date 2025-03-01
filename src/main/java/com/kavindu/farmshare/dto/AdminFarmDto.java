package com.kavindu.farmshare.dto;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockAllocation;

import java.io.Serializable;
import java.util.List;

public class AdminFarmDto implements Serializable {
    private boolean success;
    private List<Farm> farmList;
    private List<AdminPaymentDto> adminPaymentDtoList;

    public AdminFarmDto() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Farm> getFarmList() {
        return farmList;
    }

    public void setFarmList(List<Farm> farmList) {
        this.farmList = farmList;
    }

    public List<AdminPaymentDto> getAdminPaymentDtoList() {
        return adminPaymentDtoList;
    }

    public void setAdminPaymentDtoList(List<AdminPaymentDto> adminPaymentDtoList) {
        this.adminPaymentDtoList = adminPaymentDtoList;
    }
}
