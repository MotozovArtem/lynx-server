package org.peerstock.server.filestorage.repository;

import org.peerstock.server.filestorage.domain.SmallFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmallFileRepository extends MongoRepository<SmallFile, String> {
}
