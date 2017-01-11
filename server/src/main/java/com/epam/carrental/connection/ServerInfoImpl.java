package com.epam.carrental.connection;

import com.epam.carrental.tenant.RequiresTenant;
import com.epam.carrental.dto.ServerInfoDTO;
import com.epam.carrental.services.ServerInfo;
import com.epam.carrental.tenant.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class ServerInfoImpl implements ServerInfo {

    @Autowired
    Tenant tenant;

    @Override
    @RequiresTenant
    public ServerInfoDTO getServerInfo() {
        ZonedDateTime time = getCurrentTime();
        InetAddress ipAddress = getIPAddress();

       /* try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }*/
        return new ServerInfoDTO(ipAddress,time, tenant.id.get());
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
