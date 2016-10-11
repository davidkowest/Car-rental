package com.epam.carrental.dto;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.ZonedDateTime;

public class ServerInfoDTO implements Serializable {
    private ZonedDateTime time;
    private InetAddress ipAddress;

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
