package com.example.mvc.postgreSQL.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class board_dto {

    private Long id;

    private String title;

    private String writer;
}
