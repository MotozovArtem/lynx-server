package org.peerstock.server.test.util;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = {IntegrationTest.Initializer.class})
public class IntegrationTest {

    @ClassRule
    public static PsServerPostgresqlContainer dbContainer = PsServerPostgresqlContainer.getInstance();

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.datasource.url=" + dbContainer.getJdbcUrl(),
                            "spring.datasource.username=" + dbContainer.getUsername(),
                            "spring.datasource.password=" + dbContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
