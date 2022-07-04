package org.peerstock.server.filestorage.controller;

import org.peerstock.server.filestorage.domain.LargeFile;
import org.peerstock.server.filestorage.service.LargeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/large_file")
public class LargeFileController {

    
    private final LargeFileService largeFileService;

    @Autowired
    public LargeFileController(LargeFileService largeFileService) {
        this.largeFileService = largeFileService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLargeFile(@RequestParam("filename") String filename,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        String id = largeFileService.uploadLargeFile(filename, file);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/stream/{id}")
    public void streamLargeFile(@PathVariable String id, HttpServletResponse response) throws Exception {
        LargeFile largeFile = largeFileService.downloadLargeFile(id);
        FileCopyUtils.copy(largeFile.getStream(), response.getOutputStream());
    }
}
