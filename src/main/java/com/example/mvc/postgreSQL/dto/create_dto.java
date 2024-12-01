package com.example.mvc.postgreSQL.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NonNull
public class create_dto {
    private String title;
    private String data;
}
