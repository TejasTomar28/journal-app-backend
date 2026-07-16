package com.tejastomar.journalapp.repository;

import com.tejastomar.journalapp.entity.AIInsight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AIInsightRepository extends MongoRepository<AIInsight, ObjectId> {
    AIInsight findByJournalEntryId(ObjectId journalEntryId);
    List<AIInsight> findByUsername(String username);
}
