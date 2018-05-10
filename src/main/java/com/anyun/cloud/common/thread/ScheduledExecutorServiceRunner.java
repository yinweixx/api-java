package com.anyun.cloud.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledExecutorServiceRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledExecutorServiceRunner.class);
    private static final int INITIAL_DELAY = 0;
    private String name;
    private ScheduledExecutorService executor;
    private int loopTime = 1;
    private long lastStopTime;
    private Runnable runnable;

    protected abstract Runnable buildRunnable();

    public ScheduledExecutorServiceRunner withLoopTime(int loopTime) {
        if (loopTime > this.loopTime)
            this.loopTime = loopTime;
        return this;
    }




    public void start() {
        if (runnable == null)
            this.runnable = buildRunnable();
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this.runnable, INITIAL_DELAY, loopTime, TimeUnit.SECONDS);
        LOGGER.debug("Starting scheduled executor service runner [{}]", name);
    }

    public void stop() {
        LOGGER.debug("Stopping scheduled executor service runner [{}]", name);
        executor.shutdown();
        lastStopTime = System.currentTimeMillis();
    }

    public void restart() {
        LOGGER.debug("Restarting scheduled executor service runner [{}]", name);
        stop();
        start();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    public int getLoopTime() {
        return loopTime;
    }

    public long getLastStopTime() {
        return lastStopTime;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}
