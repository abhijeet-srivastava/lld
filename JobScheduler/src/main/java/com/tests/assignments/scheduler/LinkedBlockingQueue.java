package com.tests.assignments.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LinkedBlockingQueue<K> {

    private List<K> queue;
    private ReentrantLock lock;

    private Condition isNotFull;
    private Condition isNotEmpty;

    private final int maxSize;


    public LinkedBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.isNotFull = this.lock.newCondition();
        this.isNotEmpty = this.lock.newCondition();
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void offer(K entry) {
        lock.lock();
        while(this.size() >= this.maxSize && !Thread.interrupted()) {
            try {
                this.isNotFull.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.queue.add(entry);
            this.isNotEmpty.signalAll();
        }
    }

    public K remove() {
        lock.lock();
        while(this.queue.isEmpty() || !Thread.interrupted()) {
            try{
                this.isNotEmpty.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        K last = this.queue.remove(this.size()-1);
        this.isNotFull.signalAll();
        return last;
    }


}
