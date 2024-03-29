package io.vlaskz.portfolio.repository;

import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Saves User when successful")
    void savePersistUserWhenSuccessful() {

        User user = UserCreator.createUserToBeSaved();

        User savedUser = this.userRepository.save(user);


        Assertions.assertThat(savedUser).isNotNull();

        Assertions.assertThat(savedUser.getId()).isNotNull();

        Assertions.assertThat(savedUser.getName()).isEqualTo(user.getName());

    }

    @Test
    @DisplayName("Update User when successful")
    void saveUpdateUserWhenSuccessful() {

        User user = UserCreator.createUserToBeSaved();

        User savedUser = this.userRepository.save(user);

        savedUser.setName("Paarthurnax Velasquez");

        User updatedUser = this.userRepository.save(savedUser);


        Assertions.assertThat(updatedUser).isNotNull();

        Assertions.assertThat(updatedUser.getId()).isNotNull();

        Assertions.assertThat(updatedUser.getName()).isEqualTo(savedUser.getName());

    }

    @Test
    @DisplayName("Delete User when successful")
    void saveDeleteUserWhenSuccessful() {

        User user = UserCreator.createUserToBeSaved();

        User savedUser = this.userRepository.save(user);

        this.userRepository.delete(savedUser);

        Optional<User> userDeleted = this.userRepository.findById(savedUser.getId());

        Assertions.assertThat(userDeleted).isEmpty();

    }

    @Test
    @DisplayName("Returns List<User> when successful")
    void findByNameUserWhenSuccessful() {

        User user = UserCreator.createUserToBeSaved();

        User savedUser = this.userRepository.save(user);

        savedUser.setName("Paarthurnax Velasquez");

        List<User> users = this.userRepository.findByName(savedUser.getName());

        Assertions.assertThat(users)
                .isNotEmpty()
                .contains(savedUser);


    }

    @Test
    @DisplayName("Returns empty list when no user's found")
    void returnsEmptyListWhenUserIsNotFound() {


        List<User> users = this.userRepository.findByName("no user included.");

        Assertions.assertThat(users).isEmpty();


    }


    @Test
    @DisplayName("Throw ConstraintViolationException when name is empty")
    void saveThrowConstraintViolationExceptionWhenUserNameIsEmpty() {

        User user = new User();
//
//        Assertions.assertThatThrownBy(() -> this.userRepository.save(user))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.userRepository.save(user));

    }



}