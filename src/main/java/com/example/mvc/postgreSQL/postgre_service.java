package com.example.mvc.postgreSQL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class postgre_service {
    
    @Autowired
    postgre_reposiotory repo;

    public List<postgre_data> all_data(String title) {
        return repo.findByTitle(title);
    }


    public Page<postgre_data> all_data2(Pageable pageable, String title) {
        return repo.findAllByTitle(pageable, title);
    }

    //{#id, #createdAt.toEpochSecond()}"
    @Cacheable(value = "board", key = "#id", cacheManager = "contentCacheManager")
    public postgre_data get_data(Long id) {
        Optional<postgre_data> tmp = repo.findById(id);
        if(tmp.get() == null) {
            return null;
        } 
        return tmp.get();
    }

    public postgre_data create_data(postgre_data data, String user) {
        data.setWriter(user);
        postgre_data tmp = new postgre_data(data.getTitle(),data.getData(),user);
        return repo.save(tmp);
    }

    public void delete_data(Long id, String writer) {
        repo.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "board", key="#id", cacheManager = "contentCacheManager")
    public postgre_data update_data(Long id, String title, String data) {
        postgre_data update = repo.findById(id).get();
        update.setData(data);
        update.setTitle(title);
        update.setCreatedAt(LocalDateTime.now());
        return update;
    }
}
