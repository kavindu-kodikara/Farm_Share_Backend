package com.kavindu.farmshare.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable {
    private boolean success;
    private String message;
    private Object data;

    public ResponseDto() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
