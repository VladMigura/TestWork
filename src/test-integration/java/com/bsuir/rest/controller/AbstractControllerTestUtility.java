package com.bsuir.rest.controller;

import com.bsuir.rest.application.Application;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.model.Role;
import com.bsuir.rest.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public abstract class AbstractControllerTestUtility {

    UserEntity userEntity;
    final String USERNAME_VALID = "TestUser";
    final String USERNAME_INVALID = "NotTestUser";
    final String PASSWORD = "Password";
    final String ROLE_USER = "USER";
    final String TOKEN_VALUE = "0123456789";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Before
    public final void initUser() {

        userEntity = UserEntity.builder()
                .username(USERNAME_VALID)
                .hashPassword(passwordEncoder.encode(PASSWORD))
                .role(Role.valueOf(ROLE_USER))
                .build();

        userRepository.save(userEntity);
    }

    @After
    public final void clearDatabases() {

        jdbcTemplate.execute("DELETE FROM tokens");
        jdbcTemplate.execute("DELETE FROM jogs_info");
        jdbcTemplate.execute("DELETE FROM users");
    }
}
