package com.epam.carrental.services;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerInfoImpl implements ServerInfo {

    @Override
    public String getServerInfo() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = "Time now is " + timeFormat.format(new Date());
        String ipAddress = "";
        try {
            InetAddress ip;
            ip = InetAddress.getLocalHost();
            ipAddress = "Current IP address : " + ip.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time +". "+ ipAddress;
    }
}
