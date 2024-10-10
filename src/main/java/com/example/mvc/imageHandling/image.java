package com.example.mvc.imageHandling;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Getter
@Setter
@Document(collection = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class image {
    
    @Id
    private ObjectId id;

    private String name;
    
    private byte[] data = null;


}
