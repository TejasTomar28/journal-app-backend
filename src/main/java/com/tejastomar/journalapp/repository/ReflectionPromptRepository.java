package com.tejastomar.journalapp.repository;

import com.tejastomar.journalapp.entity.ReflectionPrompt;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReflectionPromptRepository extends MongoRepository<ReflectionPrompt, ObjectId> {
    List<ReflectionPrompt> findByActiveTrue();
}
