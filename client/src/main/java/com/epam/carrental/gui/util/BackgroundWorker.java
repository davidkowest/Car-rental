package com.epam.carrental.gui.util;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    failureHandler.accept(e);
                }
            }
        };
        swingWorker.execute();
    }
}
