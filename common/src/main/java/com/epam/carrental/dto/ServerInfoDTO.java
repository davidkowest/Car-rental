package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ServerInfoDTO implements Serializable {
    private InetAddress ipAddress;
    private ZonedDateTime time;
    private String tenantId;
}
