package com.specialprojectslab.simpleux.concurrent;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class UIExecutor extends AbstractExecutorService {
    @Override
    public void shutdown() {
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if ( SwingUtilities.isEventDispatchThread())
            command.run();
        else
            SwingUtilities.invokeLater(command);
    }
}
