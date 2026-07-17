package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.model.WeeklyReflectionEvent;
import com.tejastomar.journalapp.services.WeeklyReflectionProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyReflectionProducerTest {

    @Mock
    private KafkaTemplate<String, WeeklyReflectionEvent> kafkaTemplate;

    @InjectMocks
    private WeeklyReflectionProducer weeklyReflectionProducer;

    @Test
    void publish_sendsEventToKafka() {

        WeeklyReflectionEvent event =
                WeeklyReflectionEvent.builder()
                        .username("tejas")
                        .build();

        weeklyReflectionProducer.publish(event);

        verify(kafkaTemplate).send(
                "weekly-reflection-topic",
                "tejas",
                event
        );
    }
}