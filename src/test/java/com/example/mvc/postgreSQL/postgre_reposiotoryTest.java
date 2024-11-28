//package com.example.mvc.postgreSQL;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import org.springframework.data.domain.Pageable;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class postgre_reposiotoryTest {
//
//    @Autowired
//    postgre_reposiotory repo;
//
//    @Test
//    @DisplayName("find all")
//    void find_all() {
//        for(int i = 0; i < 100; i++) {
//            repo.findAll();
//        }
//    }
//
//    @Test
//    @DisplayName("find paging")
//    void find_page() {
//        for(int i = 0; i < 100; i++) {
//            String a = null;
//
//            Pageable page = PageRequest.of(0, 30);
//            repo.findAllByTitle(page,a);
//        }
//    }
//
//    @Test
//    @DisplayName("find but no data")
//    void find_no_data() {
//        repo.findAllNoData();
//    }
//}