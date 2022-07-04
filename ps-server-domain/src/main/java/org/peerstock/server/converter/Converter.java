package org.peerstock.server.converter;

public interface Converter<Domain, Dto> {
    Dto toDto(Domain domain);

    Domain toDomain(Dto dto);
}
