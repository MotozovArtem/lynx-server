package org.peerstock.server.controller;

import org.peerstock.server.domain.Domain;
import org.peerstock.server.domain.User;
import org.peerstock.server.dto.UserAddDomainDto;
import org.peerstock.server.dto.UserRegistrationDto;
import org.peerstock.server.service.DomainService;
import org.peerstock.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    private final DomainService domainService;

    @Autowired
    public RegistrationController(final UserService userService, final DomainService domainService) {
        this.userService = userService;
        this.domainService = domainService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userDto) {
        Objects.requireNonNull(userDto);
        log.debug("Registration user {}", userDto.getLogin());
        if (userDto.getDomain() == null || userDto.getName() == null ||
                userDto.getLogin() == null || userDto.getPassword() == null) {
            return new ResponseEntity<>(new IllegalArgumentException("User have illegal values"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User.Builder()
                .setId(UUID.randomUUID())
                .setLogin(userDto.getLogin())
                .setName(userDto.getName())
                .setOnline(true)
                .setPassword(userDto.getPassword())
                .setCreationTime(LocalDateTime.now())
                .setLastTimeActive(LocalDateTime.now())
                .build();
        UUID savedUserId = userService.saveUser(user);
        Domain domain = new Domain.Builder()
                .setDomain(userDto.getDomain())
                .setUser(user)
                .setId(UUID.randomUUID())
                .build();
        domainService.saveDomain(domain);
        return new ResponseEntity<>(savedUserId, HttpStatus.CREATED);
    }

    @PostMapping("/domain")
    @Transactional
    public ResponseEntity<?> registerDomain(@RequestBody UserAddDomainDto userAddDomainDto) {
        Objects.requireNonNull(userAddDomainDto);
        log.debug("Registration user {}", userAddDomainDto.getLogin());
        if (userAddDomainDto.getDomain() == null || userAddDomainDto.getLogin() == null) {
            return new ResponseEntity<>(new IllegalArgumentException("User have illegal values"),
                    HttpStatus.BAD_REQUEST);
        }
        User existingUser = userService.findUserByLogin(userAddDomainDto.getLogin());
        Domain domain = new Domain.Builder()
                .setDomain(userAddDomainDto.getDomain())
                .setUser(existingUser)
                .setId(UUID.randomUUID())
                .build();
        domainService.saveDomain(domain);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
