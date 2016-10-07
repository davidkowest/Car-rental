package com.epam.carrental.gui.controller;

import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.utils.MessageFormater;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageController {

    @Autowired
    private ServerInfo serverInfo;

    @Autowired
    private MessageView messageView;

    @Autowired
    BackgroundWorker inBackgroundWorker;

    public void getMessageForTestConnectionButton() {
        inBackgroundWorker.execute(
                serverInfo::getServerInfo,
                serverInfoDTO -> messageView.showMessage(MessageFormater.prepareMessageFromServerInfoDTO(serverInfoDTO)),
                exception -> messageView.showErrorMessage("There is no connection to server!")
        );
    }
}
