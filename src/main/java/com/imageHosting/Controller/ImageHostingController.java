package com.imageHosting.Controller;

import com.imageHosting.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/imageHosting")
public class ImageHostingController {

    @Autowired
    private FileService fileService;

    @PostMapping("/images")
    public ResponseEntity<Map<String,String>> uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String url = fileService.uploadFile(multipartFile);

        Map<String, String> response = new HashMap<>();

        response.put("URL", url);

        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/images/{}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String filename){
        byte[] file = fileService.downloadFile(filename);
        ByteArrayResource contents = new ByteArrayResource(file);
        return ResponseEntity
                .ok()
                .contentLength(file.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; file name = " + filename + ".")
                .body(contents);
    }

//    @GetMapping("/images")
//    public List<String> listBuckets() {
//        var listRequest = ListObjectsRequest.builder().bucket("my-pocket").build();
//        var objects = this.fileService.listObjects(listRequest).contents();
//        List<String> files = new ArrayList<>();
//        for(S3Object obj: objects) {
//            files.add(obj.key());
//        }
//        return files;
//    }

    @PostMapping("/test")
    public String testing(){
        return "Testing";
    }

}
