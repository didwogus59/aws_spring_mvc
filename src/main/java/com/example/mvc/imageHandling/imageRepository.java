package com.example.mvc.imageHandling;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface imageRepository extends MongoRepository<image, ObjectId>{
    List<IdOnly> findAllBy();
}
