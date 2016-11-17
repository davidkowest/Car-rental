package com.epam.carrental.services;

import com.epam.carrental.utils.Tenant;
import com.epam.carrental.dto.ServerInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class ServerInfoImpl implements ServerInfo {

    @Override
    public ServerInfoDTO getServerInfo() {
        ZonedDateTime time = getCurrentTime();
        InetAddress ipAddress = getIPAddress();

        ServerInfoDTO serverInfoDTO = new ServerInfoDTO(ipAddress,time, Tenant.getId());

       /* try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }*/
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
            log.error(e.toString());
        }
        return ip;
    }
}
