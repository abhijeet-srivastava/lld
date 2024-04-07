package com.test.assignments.handlers;

import com.test.assignments.models.Message;
import com.test.assignments.models.Topic;
import com.test.assignments.models.TopicSubscriber;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TopicHandler {
    Topic topic;
    Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        this.subscriberWorkers = new HashMap<>();
    }

    public void publish(Message message) {
        for(TopicSubscriber ts: this.topic.getTopicSubscribers()) {
            startSubscriberWorker(ts);
        }
    }

    public void startSubscriberWorker(TopicSubscriber ts) {
        String subscriberId = ts.getSubscriber().getId();
        if(!this.subscriberWorkers.containsKey(subscriberId)) {
            SubscriberWorker subscriberWorker = new SubscriberWorker(this.topic, ts);
            this.subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        SubscriberWorker subscriberWorker = this.subscriberWorkers.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}
