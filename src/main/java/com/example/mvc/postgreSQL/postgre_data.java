package com.example.mvc.postgreSQL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "spring_data")
public class postgre_data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 생성 전략 설정
    private Long id;

    @Column(name = "data")
    private String data;

    @Column(name = "title")
    private String title;

    @Column(name = "writer") // "user"를 "writer"로 변경한 경우
    private String writer;
    
    @Column(name = "created")
    private LocalDateTime createdAt;

    public postgre_data(String title, String data, String user) {
        this.title = title;
        this.data = data;
        this.writer = user;
        this.createdAt = LocalDateTime.now();
    }
}
