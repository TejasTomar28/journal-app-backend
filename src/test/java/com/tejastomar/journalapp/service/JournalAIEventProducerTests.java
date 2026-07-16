package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.constants.KafkaTopics;
import com.tejastomar.journalapp.model.JournalAIRequestedEvent;
import com.tejastomar.journalapp.services.JournalAIEventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JournalAIEventProducerTests {

    @Mock
    private KafkaTemplate<String, JournalAIRequestedEvent> kafkaTemplate;

    @InjectMocks
    private JournalAIEventProducer journalAIEventProducer;

    @Test
    void publish_sendsEventToAIInsightGenerationTopic() {
        JournalAIRequestedEvent event = JournalAIRequestedEvent.builder()
                .journalEntryId("journal-entry-id")
                .build();

        journalAIEventProducer.publish(event);

        verify(kafkaTemplate).send(KafkaTopics.AI_INSIGHT_GENERATION, "journal-entry-id", event);
    }
}
