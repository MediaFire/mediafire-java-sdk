package com.mediafire.sdk.uploading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
* Created by Chris on 1/7/2015.
*/
public class PausableExecutor extends ThreadPoolExecutor {
    private boolean isPaused;
    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unpaused = pauseLock.newCondition();
    private final List<UploadRunnable> mRunning = Collections.synchronizedList(new ArrayList<UploadRunnable>());

    public PausableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }

        mRunning.add((UploadRunnable) r);
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (r instanceof UploadRunnable) {
            UploadRunnable runnable = (UploadRunnable) r;
            mRunning.remove(runnable);
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
            for (UploadRunnable runnable : mRunning) {
                runnable.pause();
            }
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();

            for (UploadRunnable runnable : mRunning) {
                runnable.resume();
            }
        } finally {
            pauseLock.unlock();
        }
    }

    public List<UploadRunnable> getRunningTasks() {
        return mRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
