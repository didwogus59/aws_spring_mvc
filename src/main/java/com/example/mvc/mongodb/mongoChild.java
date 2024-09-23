package com.example.mvc.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("child")
public class mongoChild {
    
    @Id
    private ObjectId id;

    private String data;

    private ObjectId parent;

    public mongoChild(String data, ObjectId parent) {
        this.parent = parent;
        this.data = data;
    }
}
