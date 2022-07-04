package org.peerstock.server.filestorage.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.InputStream;

public class LargeFile {

    @Id
    private String id;

    @Field("file_name")
    private String fileName;

    @Field("stream")
    private InputStream stream;

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

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}
