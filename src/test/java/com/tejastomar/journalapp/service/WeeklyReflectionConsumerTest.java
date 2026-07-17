package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.model.WeeklyReflectionEvent;
import com.tejastomar.journalapp.services.UserService;
import com.tejastomar.journalapp.services.WeeklyReflectionConsumer;
import com.tejastomar.journalapp.services.WeeklyReflectionEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyReflectionConsumerTest {

    @Mock
    private UserService userService;

    @Mock
    private WeeklyReflectionEmailService weeklyReflectionEmailService;

    @InjectMocks
    private WeeklyReflectionConsumer weeklyReflectionConsumer;

    @Test
    void consume_processesWeeklyReflectionEvent() {

        WeeklyReflectionEvent event =
                WeeklyReflectionEvent.builder()
                        .username("tejas")
                        .build();

        User user = new User();
        user.setUserName("tejas");

        when(userService.findByUserName("tejas"))
                .thenReturn(user);

        weeklyReflectionConsumer.consume(event);

        verify(userService).findByUserName("tejas");

        verify(weeklyReflectionEmailService)
                .sendWeeklyReport(user);
    }

    @Test
    void consume_doesNothingWhenUserDoesNotExist() {

        WeeklyReflectionEvent event =
                WeeklyReflectionEvent.builder()
                        .username("tejas")
                        .build();

        when(userService.findByUserName("tejas"))
                .thenReturn(null);

        weeklyReflectionConsumer.consume(event);

        verify(userService)
                .findByUserName("tejas");

        verifyNoInteractions(weeklyReflectionEmailService);
    }
}