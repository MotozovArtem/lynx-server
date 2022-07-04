package org.peerstock.server.repository;

import org.peerstock.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, QuerydslPredicateExecutor<User> {

    User findUserByName(String name);

    User findUserByLogin(String login);

    User findUserByDomain(String domain);

    User findUserByOnline(Boolean online);
}
