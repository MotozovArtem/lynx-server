package org.peerstock.server.repository;

import org.peerstock.server.domain.Domain;
import org.peerstock.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DomainRepository extends JpaRepository<Domain, UUID>, QuerydslPredicateExecutor<Domain> {

    Domain findDomainById(UUID domain);

    Domain findDomainByDomain(String domain);

    List<Domain> findDomainByUser(User user);
}
