package org.peerstock.server.controller;

import org.peerstock.server.converter.Converter;
import org.peerstock.server.domain.Domain;
import org.peerstock.server.domain.QUser;
import org.peerstock.server.domain.User;
import org.peerstock.server.dto.UserAddDomainDto;
import org.peerstock.server.dto.UserDto;
import org.peerstock.server.repository.UserRepository;
import org.peerstock.server.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final Converter<User, UserDto> userDtoConverter;

    private final DomainService domainService;

    @Autowired
    public UserController(final UserRepository userRepository,
                          final Converter<User, UserDto> userDtoConverter,
                          final DomainService domainService) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.domainService = domainService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") UUID id) {
        return userRepository.findById(id).map(userDtoConverter::toDto).orElseThrow();
    }

    @PostMapping("/{id}/online/{online}")
    public ResponseEntity<?> setOnline(@PathVariable("id") UUID id, @PathVariable("online") Boolean isOnline) {
        Objects.requireNonNull(id);
        User user = userRepository.findById(id).orElseThrow();
        user.setOnline(isOnline);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/online")
    public List<UserDto> getActiveUsersList() {
        QUser qUser = QUser.user;
        return StreamSupport.stream(userRepository
                        .findAll(qUser.online.isTrue())
                        .spliterator(), false)
                .map(userDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        QUser qUser = QUser.user;
        return userRepository
                .findAll()
                .stream()
                .map(userDtoConverter::toDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> addDomain(@PathVariable("id") UUID id, @RequestBody UserAddDomainDto userAddDomainDto) {
        Objects.requireNonNull(userAddDomainDto);
        Objects.requireNonNull(id);
        if (userAddDomainDto.getDomain() == null || userAddDomainDto.getLogin() == null) {
            return new ResponseEntity<>(new IllegalArgumentException("User have illegal values"),
                    HttpStatus.BAD_REQUEST);
        }

        User userFromDatabase = userRepository.findById(id).orElseThrow();
        Domain newDomain = new Domain.Builder()
                .setDomain(userAddDomainDto.getDomain())
                .setUser(userFromDatabase)
                .setId(UUID.randomUUID())
                .build();
        domainService.saveDomain(newDomain);
        userFromDatabase.setLastTimeActive(LocalDateTime.now());
        User updatedUser = userRepository.save(userFromDatabase);
        return new ResponseEntity<>(userDtoConverter.toDto(updatedUser), HttpStatus.OK);
    }

}
