package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.model.WeeklyReflectionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeeklyReflectionProducer {

    @Autowired
    private KafkaTemplate<String, WeeklyReflectionEvent> kafkaTemplate;

    public void publish(WeeklyReflectionEvent event){

        kafkaTemplate.send(
                "weekly-reflection-topic",
                event.getUsername(),
                event
        );

    }

}