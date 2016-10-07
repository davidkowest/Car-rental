package com.epam.carrental.gui.utils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BackgroundWorkerTest {

    volatile boolean isEventDispatchThread;
    volatile CountDownLatch latch;

    @BeforeMethod
    public void preparationBeforeTestsMethod() {
        this.isEventDispatchThread = false;
        this.latch = new CountDownLatch(2);
    }

    @Test
    public void taskIsNotInEDT() throws InterruptedException {
        //given
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        Callable<Boolean> task = () -> SwingUtilities.isEventDispatchThread();

        Consumer<Boolean> successHandler = aBoolean -> {
            isEventDispatchThread = aBoolean;
            latch.countDown();
        };

        //when
        backgroundWorker.execute(task, successHandler, null);
        latch.countDown();
        latch.await();
        //then
        assertFalse(isEventDispatchThread);
    }

    @Test
    public void successHandlerIsInEDT() throws InterruptedException {
        //given
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        Callable<Object> task = () ->new Object();

        Consumer<Object> successHandler = o -> {
            isEventDispatchThread = SwingUtilities.isEventDispatchThread();
            latch.countDown();
        };

        //when
        backgroundWorker.execute(task, successHandler, null);
        latch.countDown();
        latch.await();
        //then
        assertTrue(isEventDispatchThread);
    }


    @Test
    public void failureHandlerIsInEDT() throws InterruptedException {
        //given
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        Callable<Object> task = () -> {
            throw new Exception();
        };
        Consumer<Exception> failureHandler = o -> {
            isEventDispatchThread = SwingUtilities.isEventDispatchThread();
            latch.countDown();
        };

        //when
        backgroundWorker.execute(task, null, failureHandler);
        latch.countDown();
        latch.await();
        //then
        assertTrue(isEventDispatchThread);
    }
}