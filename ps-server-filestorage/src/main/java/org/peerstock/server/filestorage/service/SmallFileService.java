package org.peerstock.server.filestorage.service;

import org.bson.BsonBinarySubType;
import org.bson.internal.Base64;
import org.bson.types.Binary;
import org.peerstock.server.filestorage.domain.SmallFile;
import org.peerstock.server.filestorage.repository.SmallFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SmallFileService {

    private static final Logger log = LoggerFactory.getLogger(SmallFileService.class);

    private final SmallFileRepository smallFileRepository;

    @Autowired
    public SmallFileService(final SmallFileRepository smallFileRepository) {
        this.smallFileRepository = smallFileRepository;
    }

    public String addSmallFile(String title, String fileAsBase64) throws IOException {
        log.debug("Add SmallFile object into filestorage");
        SmallFile smallFile = new SmallFile();
        smallFile.setFileName(title);
        smallFile.setFile(
                new Binary(BsonBinarySubType.BINARY, Base64.decode(fileAsBase64)));
        smallFile = smallFileRepository.insert(smallFile);
        return smallFile.getId();
    }

    public SmallFile getSmallFile(String id) {
        log.debug("Requested SmallFile object from filestorage with id: {}", id);
        return smallFileRepository.findById(id).orElse(null);
    }
}
