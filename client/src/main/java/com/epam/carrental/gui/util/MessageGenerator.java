package com.epam.carrental.gui.util;

import com.epam.carrental.dto.ServerInfoDTO;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class MessageGenerator {

    @Autowired
    private ServerInfo serverInfo;

    public void getMessageForTestConnectionButton() {

        BackgroundWorker inBackground = new BackgroundWorker();

        inBackground.execute(
                serverInfo::getServerInfo,
                serverInfoDTO -> showMessage(prepareMessageFromServerInfoDTO(serverInfoDTO)),
                exception -> showMessage("There is no connection to server!")
        );
    }

    private String prepareMessageFromServerInfoDTO(ServerInfoDTO serverInfoDTO) {
        String time = serverInfoDTO.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        String ipAddress = serverInfoDTO.getIpAddress().getHostAddress();
        String message = "Server returned " + ipAddress + " IP address. Server time is " + time;
        return message;

    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
