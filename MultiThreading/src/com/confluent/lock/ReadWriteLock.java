package com.confluent.lock;

import java.util.HashMap;
import java.util.Map;

public class ReadWriteLock {
    /**
     * Read Reentrance-
     * 1. No writiers ot writing Requests and
     * 2. Current Thread is a reader
     */
    /**
     * Write Reentrance
     * 1. No Readers and
     * 2. Current Thread is Writing
     */
    /**
     * Read to Write Reentrance
     * 1. Current Thread is only reader
     */
    /**
     * Write to Read Reentrance
     * 1. Current thread is Writer
     */

    //Read to Write Reentrance
    private Map<Thread, Integer> readThreadAcessCounts;
    private int writeAccesses;

    private int writeRequests;

    private Thread writerThread;

    public ReadWriteLock() {
        this.readThreadAcessCounts = new HashMap<>();
        this.writeAccesses = 0;
        this.writeRequests = 0;
    }

    public synchronized void lockRead() throws InterruptedException {
        while (canGrantReadAccess()) {
            wait();
        }
        Thread callingThread = Thread.currentThread();
        readThreadAcessCounts.merge(callingThread, 1, Integer::sum);
    }
    public synchronized void unlockRead() {
        Thread currentThread = Thread.currentThread();
        this.readThreadAcessCounts.merge(currentThread, -1, Integer::sum);
        if(this.readThreadAcessCounts.get(currentThread) == 0) {
            this.readThreadAcessCounts.remove(currentThread);
        }
        notifyAll();
    }

    public void lockWrite() throws InterruptedException {
        writeRequests += 1;
        while (!canGrantWriteAccess()) {
            wait();
        }
        writeRequests -= 1;
        writeAccesses += 1;
        this.writerThread = Thread.currentThread();
    }

    public void unlockWrite() {
        writeAccesses -= 1;
        if(writeAccesses == 0) {
            this.writerThread = null;
        }
        notifyAll();
    }

    private boolean canGrantReadAccess() {
        if(currentThreadIsWriter()) {
            return true;
        }
        if(this.writeAccesses > 0 || this.writeRequests > 0 || !this.currentThreadIsReader()) {
            return false;
        }
        return true;
    }

    private boolean currentThreadIsReader() {
        Thread currentThread = Thread.currentThread();
        return this.readThreadAcessCounts.containsKey(currentThread);
    }

    private boolean canGrantWriteAccess() {
        if(currentThreadIsOnlyReader()) {
            return true;
        }
        if(hasReaders() || !this.currentThreadIsWriter()) {
            return false;
        }
        return true;
    }

    private boolean currentThreadIsOnlyReader() {
        Thread currentThread = Thread.currentThread();
        return this.readThreadAcessCounts.size() == 1 && this.readThreadAcessCounts.containsKey(currentThread);
    }

    private boolean currentThreadIsWriter() {
        Thread currentThread = Thread.currentThread();
        return this.writerThread != currentThread;
    }
    private boolean hasReaders() {
        return !this.readThreadAcessCounts.isEmpty();
    }

}
