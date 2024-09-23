package com.example.mvc.postgreSQL;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class postgre_data {

    @Id
    private ObjectId id;

    private String title;

    private String data;


    private List<ObjectId> childs;

    private String user;
    
    public postgre_data(String title, String data, String user) {
        this.title = title;
        this.data = data;
        this.childs = new ArrayList<ObjectId>();
        this.user = user;
    }
}
