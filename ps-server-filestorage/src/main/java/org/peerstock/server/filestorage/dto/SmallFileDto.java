package org.peerstock.server.filestorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.peerstock.server.filestorage.domain.SmallFile;

public class SmallFileDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file")
    private String fileAsBase64;

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

    public String getFileAsBase64() {
        return fileAsBase64;
    }

    public void setFileAsBase64(String fileAsBase64) {
        this.fileAsBase64 = fileAsBase64;
    }

    public static class Builder {

        private String id;

        private String fileName;

        private String fileAsBase64;

        public SmallFileDto build() {
            SmallFileDto smallFile = new SmallFileDto();
            smallFile.setFileAsBase64(fileAsBase64);
            smallFile.setFileName(fileName);
            smallFile.setId(id);
            return smallFile;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setFileAsBase64(String fileAsBase64) {
            this.fileAsBase64 = fileAsBase64;
            return this;
        }
    }
}
