package org.peerstock.server.filestorage;

import org.peerstock.server.filestorage.configuration.MongoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MongoConfiguration.class)
public class FileStorageConfiguration {

}
