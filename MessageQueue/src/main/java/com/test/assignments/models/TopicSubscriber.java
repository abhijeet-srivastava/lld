package com.test.assignments.models;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TopicSubscriber {
    AtomicInteger offSet;
    Subscriber subscriber;

    public TopicSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.offSet = new AtomicInteger(0);
    }
}
