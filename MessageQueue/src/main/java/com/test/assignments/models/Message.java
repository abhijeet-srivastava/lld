package com.test.assignments.models;

import java.util.UUID;

public class Message {
    UUID msgId;
    String content;

    public Message(String content) {
        this.msgId = UUID.randomUUID();
        this.content = content;
    }
}
