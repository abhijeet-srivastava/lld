package com.twisha.gehc.utils;

import com.twisha.gehc.model.InputEvent;
import com.twisha.gehc.model.OutputEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {

    private Map<String, ConcurrentLinkedQueue<InputEvent>> topics;

    public MessageQueue() {
        this.topics = new ConcurrentHashMap<>();
    }

    public void sendMessage(String topic, InputEvent event) {
        this.topics.computeIfAbsent(topic, e -> new ConcurrentLinkedQueue<>()).offer(event);

    }


    public OutputEvent recieveMessage() {
        return new OutputEvent();
    }
}
