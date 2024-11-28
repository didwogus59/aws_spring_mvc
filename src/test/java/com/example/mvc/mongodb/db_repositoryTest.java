//package com.example.mvc.mongodb;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataMongoTest
////@Import(YourMongoConfig.class)
//public class db_repositoryTest {
//    @Autowired
//    db_repository repo;
//
//    @Test
//    @DisplayName("mongodb jpa create find test")
//    void create_and_find_data() {
//        String title = "title";
//        String data = "data";
//        String user = "user";
//
//        mongoData created = new mongoData(title, data, user);
//        mongoData result = repo.save(created);
//
//        assertEquals(data, result.getData());
//
//        Optional<mongoData> find = repo.findById(created.getId());
//
//        assertTrue(find.isPresent());
//
//    }
//}