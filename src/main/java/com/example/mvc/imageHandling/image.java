package com.example.mvc.imageHandling;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class image {
    
    @Id
    private ObjectId id;

    private String name;
    
    private byte[] data = null;

}
