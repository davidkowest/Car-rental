package com.epam.carrental.gui.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
@Log4j2
@Component
public class BackgroundWorker {

    public <Result> void execute(Callable<Result> task,
                                 Consumer<Result> successHandler,
                                 Consumer<Exception> failureHandler) {

        SwingWorker swingWorker = new SwingWorker<Result, Object>() {

            @Override
            protected Result doInBackground() throws Exception {
                return task.call();
            }

            @Override
            protected void done() {
                try {
                    successHandler.accept(this.get());
                } catch (ExecutionException|InterruptedException e) {
                    log.error(e);
                    failureHandler.accept(e);
                }
            }
        };
        swingWorker.execute();
    }
}
