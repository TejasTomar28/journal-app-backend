package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIConfigurationService {

    private static final String AI_SYSTEM_PROMPT_CACHE_KEY = "ai_system_prompt";
    private static final long AI_SYSTEM_PROMPT_CACHE_TTL_SECONDS = 3600L;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

    public String getSystemPrompt() {
        String cachedPrompt = redisService.get(AI_SYSTEM_PROMPT_CACHE_KEY, String.class);
        if (cachedPrompt != null && !cachedPrompt.trim().isEmpty()) {
            return cachedPrompt;
        }

        String systemPrompt = getConfiguration(AppCache.Keys.AI_SYSTEM_PROMPT);
        redisService.set(AI_SYSTEM_PROMPT_CACHE_KEY, systemPrompt, AI_SYSTEM_PROMPT_CACHE_TTL_SECONDS);
        return systemPrompt;
    }

    private String getConfiguration(AppCache.Keys key) {
        if (appCache.appCache == null) {
            throw new OpenAIServiceException("AI configuration is not available");
        }

        String value = appCache.appCache.get(key.toString());
        if (value == null || value.trim().isEmpty()) {
            throw new OpenAIServiceException("Missing AI configuration: " + key);
        }
        return value.trim();
    }
}
