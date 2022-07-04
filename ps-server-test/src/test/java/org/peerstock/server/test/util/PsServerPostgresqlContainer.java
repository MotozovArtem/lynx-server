package org.peerstock.server.test.util;

import org.testcontainers.containers.PostgreSQLContainer;

public class PsServerPostgresqlContainer  extends PostgreSQLContainer<PsServerPostgresqlContainer> {

    private static final String IMAGE_VERSION="postgres:12";

    private static PsServerPostgresqlContainer container;

    private PsServerPostgresqlContainer(){
        super(IMAGE_VERSION);
    }

    public static PsServerPostgresqlContainer getInstance(){
        if (container == null) {
            container = new PsServerPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }
}
