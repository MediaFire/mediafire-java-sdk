package com.mediafire.sdk.uploader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A light wrapper around the {@link ThreadPoolExecutor}. It allows for you to pause execution and
 * resume execution when ready. It is very handy for games that need to pause.
 *
 * @author Matthew A. Johnston
 * @see <a href="https://gist.github.com/warmwaffles/8534618">PausableThreadPoolExecutor</a>
 */
public class PausableExecutor extends ThreadPoolExecutor implements Pausable {
    private boolean isPaused = true;
    private final ReentrantLock lock;
    private final Condition condition;

    /**
     * @param corePoolSize    The size of the pool
     * @param maximumPoolSize The maximum size of the pool
     * @param keepAliveTime   The amount of time you wish to keep a single task alive
     * @param unit            The unit of time that the keep alive time represents
     * @param workQueue       The queue that holds your tasks
     * @see {@link ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue)}
     */
    public PausableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    /**
     * @param thread   The thread being executed
     * @param runnable The runnable task
     * @see {@link ThreadPoolExecutor#beforeExecute(Thread, Runnable)}
     */
    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        lock.lock();
        try {
            while (isPaused) condition.await();
        } catch (InterruptedException ie) {
            thread.interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isRunning() {
        return !isPaused;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void pause() {
        lock.lock();
        try {
            isPaused = true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void resume() {
        lock.lock();
        try {
            isPaused = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
