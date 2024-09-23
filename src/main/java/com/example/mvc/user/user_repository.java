package com.example.mvc.user;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface user_repository extends MongoRepository<user, ObjectId>  {
    Optional<user> findByName(String name);
    Optional<user> findOneByProviderIdAndProvider(String providerId, String provider);
}
