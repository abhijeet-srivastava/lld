package com.test.assignments.models;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Topic {
    String topicName;

    UUID topicId;
    List<TopicSubscriber> topicSubscribers;
    List<Message> messages;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.topicId = UUID.randomUUID();
        this.topicSubscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public synchronized   void addMessage(Message msg) {
        this.messages.add(msg);
    }
    public void addSubscriber(TopicSubscriber topicSubscriber) {
        this.topicSubscribers.add(topicSubscriber);
    }
}
