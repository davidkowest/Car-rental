package com.epam.carrental.gui.listener_actions;

import com.epam.carrental.dto.ServerInfoDTO;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class MessageGenerator {

    @Autowired
    private ServerInfo serverInfo;

    public String getMessageForTestConnectionButton(){
        String message;
        try {
            ServerInfoDTO serverInfoDTO = serverInfo.getServerInfo();
            String time = serverInfoDTO.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            String ipAddress = serverInfoDTO.getIpAddress().getHostAddress();
            message = "Server returned " + ipAddress + " IP address. Server time is " + time;
        } catch (Exception exc) {
            message = "There is no connection to server!";
        }
        return message;
    }
}
