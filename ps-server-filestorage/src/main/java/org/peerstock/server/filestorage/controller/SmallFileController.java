package org.peerstock.server.filestorage.controller;

import org.peerstock.server.filestorage.domain.SmallFile;
import org.peerstock.server.filestorage.dto.SmallFileDto;
import org.peerstock.server.filestorage.service.SmallFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/file")
public class SmallFileController {

    private final SmallFileService smallFileService;

    @Autowired
    public SmallFileController(SmallFileService smallFileService) {
        this.smallFileService = smallFileService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFile(@RequestParam("filename") String filename,
                                     @RequestParam("file") String fileAsBase64)
            throws IOException {
        String id = smallFileService.addSmallFile(filename, fileAsBase64);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable String id) {
        SmallFile smallFile = smallFileService.getSmallFile(id);
        SmallFileDto dto = new SmallFileDto.Builder()
                .setFileAsBase64(Base64.getEncoder().encodeToString(smallFile.getFile().getData()))
                .setFileName(smallFile.getFileName())
                .setId(smallFile.getId())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
