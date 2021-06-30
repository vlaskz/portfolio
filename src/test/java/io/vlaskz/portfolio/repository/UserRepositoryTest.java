package io.vlaskz.portfolio.repository;

import io.vlaskz.portfolio.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Saves User when successful")
    void savePersistUserWhenSuccessful() {
        User user = createUser();
        User savedUser = this.userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getName()).isEqualTo(user.getName());



    }

    private User createUser(){
        return User.builder()
                .name("Skyrim Velasquez")
                .email("fusrodah@icloud.com")
                .build();
    }
}