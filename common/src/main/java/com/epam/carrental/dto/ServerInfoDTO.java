package com.epam.carrental.dto;

import java.io.Serializable;

public class ServerInfoDTO implements Serializable {
    private String time;
    private String ipAddress;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
