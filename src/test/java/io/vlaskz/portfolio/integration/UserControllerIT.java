package io.vlaskz.portfolio.integration;

import io.vlaskz.portfolio.model.SysUser;
import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.repository.SysUserRepository;
import io.vlaskz.portfolio.repository.UserRepository;
import io.vlaskz.portfolio.util.UserCreator;
import io.vlaskz.portfolio.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerIT {
    @Autowired
    SysUserRepository sysUserRepository;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserRepository userRepository;

    private static final SysUser ADMIN = SysUser.builder()
            .name("Sam Velasquez")
            .email("vlaskz@icloud.com")
            .password("{bcrypt}$2a$10$K1l5qFt3yawP44o4VzH4IOTrICLc8gXcB1hcOXYhHjZ57Z0fOTSwW")
            .username("vlaskz")
            .authorities("ROLE_USER, ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("vlaskz", "striper");

            return new TestRestTemplate(restTemplateBuilder);
        }

    }

    @Test
    @DisplayName("List return list of Users inside page when successful")
    void list_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        sysUserRepository.save(ADMIN);

        String expectedName = savedUser.getName();

        PageableResponse<User> userPage = testRestTemplate.exchange("/users", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse
                        <User>>() {
                }).getBody();

        Assertions.assertThat(userPage).isNotNull();
        Assertions.assertThat(userPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(userPage.toList()
                .get(0)
                .getName())
                .isNotNull()
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName("ListAll return list of Users inside page when successful")
    void listAll_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        sysUserRepository.save(ADMIN);

        String expectedName = savedUser.getName();


        List<User> userList = testRestTemplate.exchange("/users/admin/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List
                        <User>>() {
                }).getBody();


        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList)
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(userList
                .get(0)
                .getName())
                .isNotNull()
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName("FindById returns a User when successful")
    void findById_ReturnsUserWhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        Long expectedId = savedUser.getId();

        User user = testRestTemplate.getForObject("/users/{id}", User.class, expectedId);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(expectedId);

    }

    @Test
    @DisplayName("FindByName returns a list of Users  when successful")
    void findByName_ReturnsListOfUsersWhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        String expectedName = savedUser.getName();
        String url = String.format("/users/find?name=%s", expectedName);
        List<User> userList = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List
                        <User>>() {
                }).getBody();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList).isNotEmpty();
        Assertions.assertThat(userList
                .get(0)
                .getName())
                .isNotNull();

    }

    @Test
    @DisplayName("FindByName returns empty list when User's not found")
    void findByName_ReturnsEmptyListWhenUserIsNotFound() {


        List<User> userList = testRestTemplate.exchange("/users/find?name=asdasd", HttpMethod.GET, null,
                new ParameterizedTypeReference<List
                        <User>>() {
                }).getBody();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList).isEmpty();


    }

    @Test
    @DisplayName("Save returns a User when successful")
    void save_ReturnsUserWhenSuccessful() {
        User savedUser = UserCreator.createUserToBeSaved();
        ResponseEntity userResponseEntity = testRestTemplate.postForEntity("/users", savedUser, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();


    }

    @Test
    @DisplayName("Delete returns a String when successful")
    void delete_ReturnsAStringWhenSuccessful() {

        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        savedUser.setName("Dormammu");
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/users/{id}", HttpMethod.DELETE,
                null, Void.class, savedUser.getId());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Replace returns a String when successful")
    void replace_ReturnsAStringWhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        savedUser.setName("Dormammu");
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/users", HttpMethod.PUT,
                new HttpEntity(savedUser), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }



}
