package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {

    private int size;
    private List<T> queue;

    private Lock lock = new ReentrantLock(true);

    private Condition isNotEmpty = lock.newCondition();
    private Condition isNotFull = lock.newCondition();

    public BlockingQueue(int size) {
        this.size = size;
        this.queue = new ArrayList<>(size);
    }

    public void enqueue(T element) {
        lock.lock();
        try {
            while(this.queue.size() == this.size) {
                isNotFull.await();
            }
            queue.add(element);
            isNotEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public T dequeue() {
        lock.lock();
        try {
            while (this.queue.isEmpty()) {
                isNotEmpty.await();
            }
            T element = queue.remove(queue.size()-1);
            isNotFull.signalAll();
            return element;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> bq = new BlockingQueue<>(10);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        bq.startProducerThread(bq, executor);
        bq.startConsumerThread(bq, executor);
    }

    private void startConsumerThread(BlockingQueue<Integer> bq, ExecutorService executor) {
        executor.submit(new ConsumerJob(bq, 10));
    }

    private void startProducerThread(BlockingQueue<Integer> bq, ExecutorService executor) {
        executor.submit(new ProducerJob(bq, 10));
    }

    public class ConsumerJob implements Runnable {
        private Integer count;
        private BlockingQueue<Integer> bq;
        public ConsumerJob(BlockingQueue<Integer> bq, int cnt) {
            this.count = cnt;
            this.bq = bq;
        }

        @Override
        public void run() {
            while (count-- > 0) {
                int value = bq.dequeue();
                System.out.printf("[%s] Consumed: %d\n", Thread.currentThread().getName(), value);
            }
        }
    }

    public class ProducerJob implements Runnable {
        private Integer count;
        private BlockingQueue<Integer> bq;
        public ProducerJob(BlockingQueue<Integer> bq, int cnt) {
            this.count = cnt;
            this.bq = bq;
        }

        @Override
        public void run() {
            while (count-- > 0) {
                System.out.printf("[%s] Producing: %d\n", Thread.currentThread().getName(), count);
                bq.enqueue(count);
            }
        }
    }
}
