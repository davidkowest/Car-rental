package com.epam.carrental.services;

import com.epam.carrental.dto.ServerInfoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.ZonedDateTime;
@Log4j2
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
            log.error(e);
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
            log.error(e);
        }
        return ip;
    }
}
