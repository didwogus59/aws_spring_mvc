package com.example.mvc.imageHandling;

import org.springframework.http.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/image")
public class imageController {
    
    @Autowired
    private imageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if(file.getSize() < 400000) {
            if(service.saveImage(file))
                return ResponseEntity.ok("Image uploaded successfully");
        }
        return ResponseEntity.ok("Image uploaded failed");
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> storeImage(@RequestParam("file") MultipartFile file) throws IOException {
//        if(file.getSize() < 400000) {
//            return ResponseEntity.ok(service.storeImage(file));
//        }
//        return ResponseEntity.ok("Image uploaded failed");
//    }
    @GetMapping("/list")
    public String listImage(Model model) {
        model.addAttribute("list", service.imageList());
        return "file/image_list";
    }
    
//    @GetMapping("/show/{id}")
//    public String showImage(@PathVariable ObjectId id, Model model) {
//
//        image tmp = service.getImage(id);
//        String base64Image = Base64.getEncoder().encodeToString(tmp.getData());
//        model.addAttribute("image", base64Image);
//        return "file/display";
//    }

    @GetMapping("/show/{id}")
    public String showImage(@PathVariable ObjectId id, Model model) {
        model.addAttribute("image_id", id);
        return "file/display";
    }


    @GetMapping("/api/{id}")
    public ResponseEntity<byte[]> imageApi(@PathVariable ObjectId id) {
        image tmp = service.getImage(id);
        byte[] imagebyte = tmp.getData();
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("image/webp"));
        headers.setContentLength(imagebyte.length);
        headers.setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
        return new ResponseEntity<byte[]>(imagebyte, headers, HttpStatus.OK);
    }
    
    @GetMapping("/show/api/{id}")
    public String show_image_api(@PathVariable ObjectId id, Model model) {
        model.addAttribute("id", id);
        return "file/display_api";
    }

    @GetMapping("/test")
    public String uploadPage() {
        return "file/upload";
    }

//    @GetMapping(
//            value = "/{id}",
//            produces = MediaType.IMAGE_JPEG_VALUE)
//    public @ResponseBody byte[] getImage(@PathVariable ObjectId id) {
//        return service.getImage(id).getData();
//    }
//
//    @GetMapping("/webp/{id}")
//    public ResponseEntity<byte[]> getWebP(@PathVariable ObjectId id) throws IOException {
//        image image = service.getImage(id);
//        byte[] webpData = image.getData();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("image/webp"));
//        headers.setContentLength(webpData.length);
//        headers.setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
//        return new ResponseEntity<>(webpData, headers, HttpStatus.OK);
//    }
}
