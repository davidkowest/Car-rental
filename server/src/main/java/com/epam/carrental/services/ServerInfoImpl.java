package com.epam.carrental.services;

import com.epam.carrental.dto.ServerInfoDTO;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.ZonedDateTime;
@Component
public class ServerInfoImpl implements ServerInfo {

    @Override
    public ServerInfoDTO getServerInfo() {
        ZonedDateTime time = getCurrentTime();
        InetAddress ipAddress = getIPAddress();

        ServerInfoDTO serverInfoDTO = new ServerInfoDTO(ipAddress,time);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
