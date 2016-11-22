package com.epam.carrental.gui.utils;

import com.epam.carrental.dto.ServerInfoDTO;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MessageFormatter {

    private MessageFormatter(){}

    public static String prepareMessageFromServerInfoDTO(ServerInfoDTO serverInfoDTO) {
        String time = serverInfoDTO.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        String ipAddress = serverInfoDTO.getIpAddress().getHostAddress();
        String tenantId=serverInfoDTO.getTenantId();
        return tenantId+" @ [" + ipAddress + "], time: [" + time+"]";
    }
}
