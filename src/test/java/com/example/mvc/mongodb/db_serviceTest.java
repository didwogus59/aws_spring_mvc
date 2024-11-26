//package com.example.mvc.mongodb;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//
//@ExtendWith(MockitoExtension.class)
//public class db_serviceTest {
//
//    @Mock
//    db_repository repo;
//
//    @InjectMocks
//    db_service service;
//
////    @BeforeEach
////    void setUp(){
////        System.out.println("test set up");
////        service = new db_service(repo);
////
////    }
//
//    @Test
//    @DisplayName("데이터 생성 성공")
//    void createDataSuccess(){
//        String title = "title";
//        String data = "data";
//        String user = "user";
//
//        mongoData tmp = new mongoData(title, data, user);
//        ArgumentCaptor<mongoData> captor = ArgumentCaptor.forClass(mongoData.class);
//        Mockito.when(repo.insert(Mockito.any(mongoData.class))).thenReturn(tmp);
////        Mockito.when(repo.save(captor.capture())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
////        Mockito.any(mongoData.class)
//
//        mongoData tmp1 = service.create_data(title, data, user);
////        mongoData tmp1 = repo.save(tmp);
//        assertNotNull(tmp1);
//        assertEquals(data, tmp1.getData());
//        assertEquals(user, tmp1.getUser());
//
//        // Verify that save was called
//        Mockito.verify(repo).insert(Mockito.any(mongoData.class));
//    }
//}
