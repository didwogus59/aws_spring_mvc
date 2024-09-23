package com.example.mvc.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface child_repository extends MongoRepository<mongoChild, ObjectId> {
    List<mongoChild> findByParent(ObjectId id);
    void deleteByParent(ObjectId id);
}
