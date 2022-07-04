package org.peerstock.server.test.user;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peerstock.server.dto.UserRegistrationDto;
import org.peerstock.server.Application;
import org.peerstock.server.domain.User;
import org.peerstock.server.dto.UserDto;
import org.peerstock.server.repository.UserRepository;
import org.peerstock.server.test.util.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class UserControllerTest extends ControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private UUID user1Id;

    private UUID user2Id;

    private User user1;

    private UserDto user1Dto;

    private User user2;

    private UserDto user2Dto;

    @Before
    public void createTestUsers() {
        user1 = new User();
        user1.setId(user1Id);
        user1.setLogin("user1");
        user1.setName("user_one");
        user1.setOnline(true);
        user1.setLastTimeActive(LocalDateTime.now());
        user1.setPassword("password");
        user1.setCreationTime(LocalDateTime.now());
        user1 = userRepository.save(user1);
        user1Id = user1.getId();
        user1Dto = modelMapper.map(user1, UserDto.class);

        user2 = new User();
        user2.setId(user2Id);
        user2.setLogin("user2");
        user2.setName("user_two");
        user2.setOnline(false);
        user2.setLastTimeActive(LocalDateTime.now());
        user2.setPassword("password");
        user2.setCreationTime(LocalDateTime.now());
        user2 = userRepository.save(user2);
        user2Id = user2.getId();
        user2Dto = modelMapper.map(user2, UserDto.class);

    }

    @After
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void getUserList() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(user1Dto, user2Dto))));
    }

    @Test
    public void getUserById() throws Exception {
        mvc.perform(get("/user/" + user1Id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user1Dto)));

    }

    @Test
    public void createUser() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setName("user_name");
        registrationDto.setDomain("192.168.0.3");
        registrationDto.setPassword("passwd");

        String userUuid = mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(userUuid);
    }

    @Test
    public void createUserWithoutLogin() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setName("user_name");
        registrationDto.setDomain("192.168.0.1");
        registrationDto.setPassword("passwd");

        String message = mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(message);
        assertThat(message, containsString("User have illegal values"));
    }

    @Test
    public void createUserWithoutName() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setDomain("192.168.0.1");
        registrationDto.setPassword("passwd");

        String message = mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(message);
        assertThat(message, containsString("User have illegal values"));
    }

    @Test
    public void createUserWithoutIpAddressAndPort() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setName("user_name");
        registrationDto.setPassword("passwd");

        String message = mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(message);
        assertThat(message, containsString("User have illegal values"));
    }

    @Test
    public void createUserWithoutPassword() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setName("user_name");
        registrationDto.setDomain("192.168.0.1");

        String message = mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(message);
        assertThat(message, containsString("User have illegal values"));
    }

    @Test
    public void createUserWithDuplicateLogin() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user1");
        registrationDto.setName("user_name");
        registrationDto.setDomain("192.168.0.1");
        registrationDto.setPassword("passwd");

        assertThrows(NestedServletException.class, () -> {
            mvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registrationDto))
                    .accept(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    public void createUserWithDuplicateName() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setName("user_one");
        registrationDto.setDomain("192.168.0.1");
        registrationDto.setPassword("passwd");

        assertThrows(NestedServletException.class, () -> {
            mvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registrationDto))
                    .accept(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    public void createUserWithDuplicateIpAddressAndPort() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setLogin("user_login");
        registrationDto.setName("user_name");
        registrationDto.setDomain("192.168.0.1");
        registrationDto.setPassword("passwd");

        assertThrows(NestedServletException.class, () -> {
            mvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registrationDto))
                    .accept(MediaType.APPLICATION_JSON));
        });
    }

}
