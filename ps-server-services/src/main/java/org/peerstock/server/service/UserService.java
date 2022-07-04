package org.peerstock.server.service;

import org.peerstock.server.domain.Role;
import org.peerstock.server.domain.User;
import org.peerstock.server.repository.RoleRepository;
import org.peerstock.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(UUID userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public UUID saveUser(User user) {
        User userFromDB = userRepository.findUserByLogin(user.getUsername());

        if (userFromDB != null) {
            log.debug("User with same login {} exist. Returning existing user ID", user.getLogin());
            return user.getId();
        }
        log.debug("Creating new user {}", user.getLogin());
        user.setRoles(
                Collections.singleton(
                        new Role.Builder()
                                .setId(UUID.randomUUID())
                                .setName("ROLE_USER")
                                .build()
                )
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public boolean deleteUser(UUID userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean disableUser(UUID userId) {
        // TODO: Implement
        return false;
    }
}
