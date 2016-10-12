package com.epam.carrental.controller;

import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.utils.MessageFormatter;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerInfoController {

    @Autowired
    private ServerInfo serverInfo;

    @Autowired
    private MessageView messageView;

    @Autowired
    private BackgroundWorker inBackgroundWorker;

    public void getMessageForTestConnectionButton() {
        inBackgroundWorker.execute(
                serverInfo::getServerInfo,
                serverInfoDTO -> messageView.showMessage(MessageFormatter.prepareMessageFromServerInfoDTO(serverInfoDTO)),
                exception -> messageView.showErrorMessage(exception.getCause().getMessage())
        );
    }
}
