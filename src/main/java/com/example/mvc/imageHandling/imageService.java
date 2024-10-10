package com.example.mvc.imageHandling;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

@Service
public class imageService {
    
    String[] types = {"jpg", "jpeg", "png", "gif"};

    @Autowired
    private imageRepository repo;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public List<IdOnly> imageList() {
        return repo.findAllBy();
    }
    public boolean saveImage(MultipartFile file) {
        if(typeCheck(file.getOriginalFilename())) {
            image image = new image();
            image.setName(file.getOriginalFilename());
            try {
                image.setData(file.getBytes());
                repo.save(image);
                return true;
            } catch(Exception e) {
                return false;
            }
        }
        return false;
    }
    public boolean deleteImage(ObjectId id) {
        try {
            repo.deleteById(id);;
        }catch (Exception e) {
            return false;
        }
        return true;
    }
    public image getImage(ObjectId id) {
        return repo.findById(id).get();
    }

    boolean typeCheck(String name) {
        String myType = name.substring(name.lastIndexOf(".")+1);
        for(String type : types) {
            if(myType.equals(type))
                return true;
        }
        System.out.printf("%s\n", myType);
        return false;
    }

    public String storeImage(MultipartFile file) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", file.getSize());

        Object fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);

        return fileId.toString();
    }
}
