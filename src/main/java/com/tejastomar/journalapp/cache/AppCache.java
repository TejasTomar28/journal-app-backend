package com.tejastomar.journalapp.cache;

import com.tejastomar.journalapp.entity.ConfigJournalAppEntity;
import com.tejastomar.journalapp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum Keys{
        WEATHER_API,
        OPENAI_MODEL,
        OPENAI_BASE_URL,
        OPENAI_TEMPERATURE,
        OPENAI_MAX_TOKENS,
        AI_SYSTEM_PROMPT;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
