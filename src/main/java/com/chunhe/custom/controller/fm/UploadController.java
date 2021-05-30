package com.chunhe.custom.controller.fm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/upload")
public class UploadController {

//    @Autowired
//    private UploadService uploadService;
//
//    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity queryMenuList(@RequestPart("file") MultipartFile file,
//                                        @RequestParam(required = false) String dir, HttpServletRequest request) throws Exception {
//        String filePath = uploadService.uploadFile(request, file, dir);
//        System.out.println(filePath);
//        return ResponseEntity.status(HttpStatus.OK).body(filePath);
//    }
}
