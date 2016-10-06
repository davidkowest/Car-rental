package com.epam.carrental.services;

import com.epam.carrental.dto.ServerInfoDTO;

import java.net.InetAddress;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ServerInfoImpl implements ServerInfo {

    @Override
    public ServerInfoDTO getServerInfo() {
        ZonedDateTime time = getCurrentTime();
        InetAddress ipAddress = getIPAddress();

        ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
        serverInfoDTO.setIpAddress(ipAddress);
        serverInfoDTO.setTime(time);

        return serverInfoDTO;
    }

    private ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

    private InetAddress getIPAddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
