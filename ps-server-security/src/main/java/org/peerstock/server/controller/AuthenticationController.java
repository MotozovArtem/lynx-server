package org.peerstock.server.controller;

import org.peerstock.server.domain.AuthToken;
import org.peerstock.server.domain.LoginUser;
import org.peerstock.server.domain.User;
import org.peerstock.server.repository.UserRepository;
import org.peerstock.server.service.UserService;
import org.peerstock.server.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userRepository.findUserByLogin(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ResponseEntity<>(new AuthToken(token, user.getUsername()), HttpStatus.OK);
    }

}