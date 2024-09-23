package com.example.mvc.mongodb;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Transaction;
import org.springframework.stereotype.Service;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Service
@Slf4j
public class db_service {
    @Autowired
    private db_repository repository;

    @Autowired
    private child_repository repositoryC;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<mongoData> all_data() {
        return repository.findAll();
    }


    public List<mongoData> all_data2() {
        return mongoTemplate.findAll(mongoData.class);
    }

    public Optional<mongoData> get_data(ObjectId id) {
        return repository.findById(id);
    }

    public List<mongoData> get_data2(ObjectId id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongoTemplate.find(query, mongoData.class);
    }

    public mongoData create_data(String title, String data, String user) {
        mongoData test = repository.insert(new mongoData(title, data, user));
        return test;
    }
    public mongoData create_data2(String title, String data, String user) {
        mongoData test = new mongoData(title, data, user);
        repository.save(test);
        return test;
    }


    public mongoData create_data3(String title, String data, String user) {
        mongoData test = new mongoData(title, data, user);
        return mongoTemplate.insert(test);
    }

    public mongoData update_data(ObjectId id, String title, String data, String name) {
        Optional<mongoData> tmp = repository.findById(id);
        if(tmp.isPresent()) {

            mongoData test = tmp.get();
            if(!test.getUser().equals("null") && !test.getUser().equals(name)) {
                return null;
            }
            if(title != null) {
                test.setTitle(title);
            }
            if(data != null)
                test.setData(data);

            repository.save(test);

            return test;
        }
        return null;

    }

    public void update_data2(ObjectId id, String title, String data) {
        //특정 컬렉션에 대한 쿼리 작성
        Query query = Query.query(Criteria.where("_id").is(id));
        //update할 필드와 값 설정
        Update update = new Update().update("title", title).update("data", data);
        //update 실행
        mongoTemplate.updateFirst(query, update, mongoData.class);
    }

    public void delete_data(ObjectId id, String name) {
        Optional<mongoData> tmp = repository.findById(id);
        mongoData test = tmp.get();
        if(test.getUser().equals("null") || test.getUser().equals(name)) {
                repositoryC.deleteByParent(id);
                repository.deleteById(id);
        }
    }

    public List<mongoChild> all_child(mongoData parent) {

        return repositoryC.findByParent(parent.getId());
    }

    //query를 따로 지정하여 만든 update 속도가 빠르다
    public mongoChild create_child2(ObjectId parentId, String data) {
        Query query = Query.query(Criteria.where("_id").is(parentId));
        mongoChild child = repositoryC.save(new mongoChild(data, parentId));

        Update update = new Update().push("childs", child.getId());

        mongoData update_test = mongoTemplate.findAndModify(query, update, mongoData.class, "data");

        if(update_test == null) {
            System.out.println("no mongoData error");
        }
        return child;
    }


    public void delete_child(ObjectId parentId, ObjectId childId) {
        Query query = Query.query(Criteria.where("_id").is(parentId));
        Update update = new Update().pull("childs", childId);
        mongoTemplate.updateFirst(query, update, Transaction.class, "test");
        if(childId != null)
            repositoryC.deleteById(childId);
    }


}
