package org.peerstock.server.filestorage.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.peerstock.server.filestorage.domain.LargeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LargeFileService {

    private static final Logger log = LoggerFactory.getLogger(LargeFileService.class);

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations operations;

    @Autowired
    public LargeFileService(final GridFsTemplate gridFsTemplate,
                            final GridFsOperations operations) {
        this.gridFsTemplate = gridFsTemplate;
        this.operations = operations;
    }

    public String uploadLargeFile(String title, MultipartFile file) throws IOException {
        log.debug("Uploading LargeFile into filestorage");
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "largefile");
        metaData.put("file_name", title);
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData);
        return id.toString();
    }

    public LargeFile downloadLargeFile(String id) throws IllegalStateException, IOException {
        log.debug("Downloading LargeFile from file storage with id: {}", id);
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null && file.getMetadata() != null) {
            LargeFile video = new LargeFile();
            video.setFileName(file.getMetadata().get("file_name").toString());
            video.setStream(operations.getResource(file).getInputStream());
            return video;
        } else {
            return null;
        }
    }
}
