package com.example.mvc.postgreSQL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.mvc.postgreSQL.dto.board_dto;
import com.example.mvc.postgreSQL.dto.create_dto;
import com.example.mvc.postgreSQL.dto.data_dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class postgre_service {
    
    @Autowired
    postgre_reposiotory repo;

    public List<postgre_data> all_data(String title) {
        return repo.findByTitle(title);
    }


    public Page<board_dto> all_data2(Pageable pageable, String title) {
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

    @Transactional
    public void create_data(create_dto data, String user) {
        postgre_data tmp = new postgre_data(data, user);
        repo.save(tmp);
    }

    @Transactional
    public void delete_data(Long id, String writer) {
        Optional<postgre_data> tmp = repo.findById(id);
        if(tmp.get().getWriter() == null) {
            repo.deleteById(id);
        }
        else if(writer!=null) {
            if(tmp.get().getWriter().equals(writer)) {
                repo.delete(tmp.get());
            }
        }
    }

    @Transactional
    @CacheEvict(value = "board", key="#id", cacheManager = "contentCacheManager")
    public data_dto update_data(Long id, data_dto data, String writer) {
        postgre_data update = repo.findById(id).get();
        if(update.getWriter() != null) {
            if (!update.getWriter().equals(writer)) {
                return update.getDto();
            }
        }
        update.setData(data.getData());
        update.setTitle(data.getTitle());
        update.setCreatedAt(LocalDateTime.now());
        return update.getDto();
    }
}
