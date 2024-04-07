package com.test.assignments;

import com.test.assignments.handlers.TopicHandler;
import com.test.assignments.models.Message;
import com.test.assignments.models.Subscriber;
import com.test.assignments.models.Topic;
import com.test.assignments.models.TopicSubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Queue {

    Map<String, TopicHandler> topicHandlers;

    public Queue() {
        this.topicHandlers = new HashMap<>();
    }

    public Topic createTopic(String topicName) {
        Topic topic = new Topic(topicName);
        UUID topicId = topic.getTopicId();
        TopicHandler topicHandler = new TopicHandler(topic);
        this.topicHandlers.put(topicId.toString(), topicHandler);
        return topic;
    }

    public void createSubscription(Topic topic, Subscriber subscriber) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
    }

    public void publish(Topic topic, Message msg) {
        topic.addMessage(msg);
        new Thread(() -> topicHandlers.get(topic.getTopicId().toString()).publish(msg)).start();

    }
    public void resetOffset(@NonNull final Topic topic, @NonNull final Subscriber subscriber, @NonNull final Integer newOffset) {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffSet().set(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getId() + " offset reset to: " + newOffset);
                new Thread(() -> topicHandlers.get(topic.getTopicId().toString()).startSubscriberWorker(topicSubscriber)).start();
                break;
            }
        }
    }
}
