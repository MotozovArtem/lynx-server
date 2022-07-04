package org.peerstock.server.filestorage.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "org.peerstock.server.filestorage")
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "peerstock-filestorage";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://admin:admin@localhost:27017/" + getDatabaseName() + "?authSource=admin");
//        MongoCredential credential = MongoCredential.createScramSha1Credential("admin", getDatabaseName(), "admin".toCharArray());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .credential(credential)
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return List.of("org.peerstock.server.filestorage");
    }
}
