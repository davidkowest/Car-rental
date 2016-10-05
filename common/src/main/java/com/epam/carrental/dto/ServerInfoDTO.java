package com.epam.carrental.dto;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalTime;

public class ServerInfoDTO implements Serializable {
    private LocalTime time;
    private InetAddress ipAddress;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
