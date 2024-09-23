package com.example.mvc.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class mongoData {

    @Id
    private ObjectId id;

    private String title;

    private String data;


    private List<ObjectId> childs;

    private String user;
    
    public mongoData(String title, String data, String user) {
        this.title = title;
        this.data = data;
        this.childs = new ArrayList<ObjectId>();
        this.user = user;
    }
}
