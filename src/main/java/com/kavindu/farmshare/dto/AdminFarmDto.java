package com.kavindu.farmshare.dto;

import com.kavindu.farmshare.entity.Farm;

import java.io.Serializable;
import java.util.List;

public class AdminFarmDto implements Serializable {
    private boolean success;
    private List<Farm> farmList;

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
}
