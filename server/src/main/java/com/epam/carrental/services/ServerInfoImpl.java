package com.epam.carrental.services;

import com.epam.carrental.dto.ServerInfoDTO;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerInfoImpl implements ServerInfo {

    @Override
    public ServerInfoDTO getServerInfo() {
        String time = getCurrentTime();
        String ipAddress = getIPAddress();

        ServerInfoDTO serverInfoDTO=new ServerInfoDTO();
        serverInfoDTO.setIpAddress(ipAddress);
        serverInfoDTO.setTime(time);

        return serverInfoDTO;
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(new Date());
    }

    private String getIPAddress() {
        String ipAddress = null;
        try {
            InetAddress ip;
            ip = InetAddress.getLocalHost();
            ipAddress = ip.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
