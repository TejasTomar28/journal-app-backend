package com.tejastomar.journalapp.scheduler;

import com.tejastomar.journalapp.cache.AppCache;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.model.WeeklyReflectionEvent;
import com.tejastomar.journalapp.repository.UserRepositoryImpl;
import com.tejastomar.journalapp.services.WeeklyReflectionEmailService;
import com.tejastomar.journalapp.services.WeeklyReflectionProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private WeeklyReflectionProducer weeklyReflectionProducer;

    @Autowired
    private WeeklyReflectionEmailService weeklyReflectionEmailService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void publishWeeklyReflectionRequests() {

        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            WeeklyReflectionEvent event =
                    WeeklyReflectionEvent.builder()
                            .username(user.getUserName())
                            .build();
            try {
                log.info("Publishing weekly reflection event for {}", user.getUserName());
                weeklyReflectionProducer.publish(event);
            } catch (Exception e) {
                log.error("Kafka publish failed. Falling back to direct email.", e);
                weeklyReflectionEmailService.sendWeeklyReport(user);
            }
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
