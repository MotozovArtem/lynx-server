package org.peerstock.server.filestorage.domain;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "small-file")
public class SmallFile {
    @Id
    private String id;

    @Field("file_name")
    private String fileName;

    @Field("file")
    private Binary file;

    @Field("creation_date")
    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Binary getFile() {
        return file;
    }

    public void setFile(Binary file) {
        this.file = file;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
