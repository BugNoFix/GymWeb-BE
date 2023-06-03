package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.security.customAnnotation.All;
import com.marcominaudo.gymweb.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/download")
public class FileController {
    @Autowired
    FilesStorageService filesStorageService;

    /*
    * Download file
    * */
    @All
    @GetMapping("/{folder}/{filename}")
    public ResponseEntity<Resource> download(@PathVariable("folder") String folder, @PathVariable("filename") String filename) throws IOException {
        Resource file = filesStorageService.load(folder, filename);
        String headerValue = "attachment; filename=\"" + filename + ".pdf" + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
