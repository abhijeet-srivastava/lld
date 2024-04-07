package com.test.assignments.handlers;

import com.test.assignments.models.Message;
import com.test.assignments.models.Topic;
import com.test.assignments.models.TopicSubscriber;
import lombok.SneakyThrows;

public class SubscriberWorker implements Runnable {

    Topic topic;
    TopicSubscriber topicSubscriber;

    public SubscriberWorker(Topic topic, TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (this.topicSubscriber) {
            do {
                int currOffset = this.topicSubscriber.getOffSet().get();
                while (currOffset > this.topic.getMessages().size()) {
                    this.topicSubscriber.wait();
                }
                Message message = topic.getMessages().get(currOffset);
                this.topicSubscriber.getSubscriber().consumeMessage(message);
                this.topicSubscriber.getOffSet().compareAndSet(currOffset, currOffset + 1);
            } while (true);
        }
    }
    synchronized void wakeUpIfNeeded() {
        this.topicSubscriber.notifyAll();
    }
}
