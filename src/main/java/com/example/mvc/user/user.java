package com.example.mvc.user;

import java.io.Serializable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Document(collection = "user")
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class user implements Serializable{
    
    @Id
    private ObjectId id;
    
    private String name;

    private String password;

    private String role;

    private String email;
    
    private String provider;

    private String providerId;

    user(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = "ROLE_USER";
    }

    user(String name, String password) {
        this.name = name;
        this.password = password;
        this.email = "noEmail";
        this.role = "ROLE_USER";
    }
    
}
