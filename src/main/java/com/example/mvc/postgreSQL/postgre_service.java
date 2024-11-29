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
    public data_dto get_data(Long id) {
        Optional<postgre_data> tmp = repo.findById(id);
        if(tmp.get() == null) {
            return null;
        } 
        return tmp.get().getDto();
    }

    public postgre_data create_data(data_dto data, String user) {
        data.setWriter(user);
        postgre_data tmp = new postgre_data(data);
        return repo.save(tmp);
    }

    public void delete_data(Long id, String writer) {
        repo.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "board", key="#id", cacheManager = "contentCacheManager")
    public data_dto update_data(Long id, data_dto data) {
        postgre_data update = repo.findById(id).get();
        update.setData(data.getData());
        update.setTitle(data.getTitle());
        update.setCreatedAt(LocalDateTime.now());
        return update.getDto();
    }
}
