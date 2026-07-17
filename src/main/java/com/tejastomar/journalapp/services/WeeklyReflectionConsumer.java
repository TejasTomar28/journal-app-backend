package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.model.WeeklyReflectionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeeklyReflectionConsumer {

    @Autowired
    private UserService userService;

    @Autowired
    private WeeklyReflectionEmailService weeklyReflectionEmailService;

    @KafkaListener(
            topics = "weekly-reflection-topic",
            groupId = "weekly-reflection-group"
    )
    public void consume(WeeklyReflectionEvent event){

        User user = userService.findByUserName(event.getUsername());
        log.info("Processing weekly reflection for {}", event.getUsername());
        if(user == null){
            return;
        }

        weeklyReflectionEmailService.sendWeeklyReport(user);
    }

}