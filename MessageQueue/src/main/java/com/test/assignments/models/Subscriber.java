package com.test.assignments.models;

public interface Subscriber {

    String getId();
    void consumeMessage(Message message);
}
