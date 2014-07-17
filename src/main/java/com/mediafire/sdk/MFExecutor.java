package com.mediafire.sdk;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by  on 5/24/2014.
 */
public class MFExecutor extends ThreadPoolExecutor {
    private boolean isPaused;
    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unPaused = pauseLock.newCondition();

    public MFExecutor(int poolSize, BlockingQueue<Runnable> workQueue) {
        super(poolSize, poolSize, 250, TimeUnit.MILLISECONDS, workQueue, Executors.defaultThreadFactory(), new DiscardOldestPolicy());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) unPaused.await();
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }
}