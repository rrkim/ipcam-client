package com.rrkim.dto;

import com.rrkim.utility.SecureKey;

import java.util.Map;

public class IPCamSecureKeyResponse {

    Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
