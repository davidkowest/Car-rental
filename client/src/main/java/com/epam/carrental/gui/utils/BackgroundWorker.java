package com.epam.carrental.gui.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
@Slf4j
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
                    log.error(e.toString());
                    failureHandler.accept(e);
                }
            }
        };
        swingWorker.execute();
    }

    public  void execute(Runnable command,
                                 Runnable successHandler,
                                 Consumer<Exception> failureHandler) {

        execute(Executors.callable(command), result->successHandler.run(), failureHandler);
    }
}
