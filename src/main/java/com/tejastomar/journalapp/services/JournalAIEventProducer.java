package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.constants.KafkaTopics;
import com.tejastomar.journalapp.model.JournalAIRequestedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JournalAIEventProducer {

    @Autowired
    private KafkaTemplate<String, JournalAIRequestedEvent> kafkaTemplate;

    public void publish(JournalAIRequestedEvent event) {
        kafkaTemplate.send(KafkaTopics.AI_INSIGHT_GENERATION, event.getJournalEntryId(), event);
    }
}
