package com.example.mvc.postgreSQL;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postgre_reposiotory extends JpaRepository<postgre_data, ObjectId> {

}