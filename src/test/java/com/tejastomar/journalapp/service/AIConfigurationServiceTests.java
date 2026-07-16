package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.cache.AppCache;
import com.tejastomar.journalapp.services.AIConfigurationService;
import com.tejastomar.journalapp.services.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AIConfigurationServiceTests {

    @Mock
    private RedisService redisService;

    @Spy
    private AppCache appCache = new AppCache();

    @InjectMocks
    private AIConfigurationService aiConfigurationService;

    @Test
    void getSystemPrompt_usesRedisWhenCachedPromptExists() {
        when(redisService.get("ai_system_prompt", String.class)).thenReturn("Cached system prompt");

        String systemPrompt = aiConfigurationService.getSystemPrompt();

        assertEquals("Cached system prompt", systemPrompt);
        verify(redisService, never()).set(any(), any(), any());
    }

    @Test
    void getSystemPrompt_loadsFromAppCacheAndStoresInRedisWhenNotCached() {
        appCache.appCache = new HashMap<>();
        appCache.appCache.put(AppCache.Keys.AI_SYSTEM_PROMPT.toString(), "Mongo-backed system prompt");
        when(redisService.get("ai_system_prompt", String.class)).thenReturn(null);

        String systemPrompt = aiConfigurationService.getSystemPrompt();

        assertEquals("Mongo-backed system prompt", systemPrompt);
        verify(redisService).set("ai_system_prompt", "Mongo-backed system prompt", 3600L);
    }
}
