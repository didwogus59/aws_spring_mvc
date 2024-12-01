package com.example.mvc.postgreSQL;

import java.util.List;

import com.example.mvc.postgreSQL.dto.board_dto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface postgre_reposiotory extends JpaRepository<postgre_data, Long> {

    @Query("select data from postgre_data data where (:title is null or data.title Like %:title%)")
    List<postgre_data> findByTitle(@Param("title") String title);

    // @Query("select p from spring_data where (:title is null or p.title = :title) and (:data is null or p.data = :data) and (:title is null or p.writer = :writer)")
    // List<postgre_data> findByTitleAndDataAndWriter(@Param("title") String title, @Param("data") String data, @Param("writer") String writer);

    // @SuppressWarnings("null")
    // Page<postgre_data> findAllByTitle(Pageable pageable);

    @Query("select new com.example.mvc.postgreSQL.dto.board_dto(data.id, data.title, data.writer) from postgre_data data where :title is null or data.title Like %:title%")
    @SuppressWarnings("null")
    Page<board_dto> findAllByTitle(Pageable pageable, @Param("title") String title);
}