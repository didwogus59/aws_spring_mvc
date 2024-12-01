package com.example.mvc.postgreSQL.dto;

import com.example.mvc.postgreSQL.postgre_data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class data_dto {

    private Long id;

    private String data;

    private String title;

    private String writer;

    private LocalDateTime createdAt;

    public data_dto(postgre_data data) {
        this.id = data.getId();
        this.data = data.getData();
        this.title = data.getTitle();
        this.writer = data.getWriter();
        this.createdAt = data.getCreatedAt();
    }

}
