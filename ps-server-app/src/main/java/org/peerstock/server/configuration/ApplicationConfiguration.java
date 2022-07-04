package org.peerstock.server.configuration;

import org.modelmapper.ModelMapper;
import org.peerstock.server.filestorage.FileStorageConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableAutoConfiguration
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("org.peerstock.server")
@Import({PsSecurityConfiguration.class,
        RestConfiguration.class,
        FileStorageConfiguration.class})
public class ApplicationConfiguration {
    @Bean
    public ModelMapper createModelMapperBean() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
