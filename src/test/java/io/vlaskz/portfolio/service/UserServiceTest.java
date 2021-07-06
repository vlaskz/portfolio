package io.vlaskz.portfolio.service;

import io.vlaskz.portfolio.controller.UserController;
import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.repository.UserRepository;
import io.vlaskz.portfolio.request.UserPostRequestBody;
import io.vlaskz.portfolio.request.UserPutRequestBody;
import io.vlaskz.portfolio.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createValidUser()));
        BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(userPage);

        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));


    }


    @Test
    @DisplayName("List return list of Users inside page when successful")
    void list_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {

        List<User> userList = userService.listAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList).isNotEmpty();
        Assertions.assertThat(userList.get(0).getName()).isNotBlank();


    }


    @Test
    @DisplayName("ListAll return list of Users inside page when successful")
    void listAll_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {

        List<User> userList = userService.listAll();


        Assertions.assertThat(userList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);


    }

    @Test
    @DisplayName("FindById returns a User when successful")
    void findById_ReturnsUserWhenSuccessful() {
        Long userId = UserCreator.createValidUser().getId();

        User user = userService.findByIdOrThrowBadRequestException(userId);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(userId);

    }


    @Test
    @DisplayName("FindByName returns a list of Users  when successful")
    void findByName_ReturnsListOfUsersWhenSuccessful() {

        List<User> userList = userService.findByName("name");

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


        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> userList = userService.findByName("name");
        Assertions.assertThat(userList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save returns a User when successful")
    void save_ReturnsUserWhenSuccessful() {

        User user = userService.save(new UserPostRequestBody());

        Assertions.assertThat(user)
                .isNotNull();
    }

    @Test
    @DisplayName("Delete returns a String when successful")
    void delete_ReturnsAStringWhenSuccessful() {

        String msg = userService.delete(1L);

        Assertions.assertThat(msg)
                .isNotNull()
                .isNotBlank()
                .hasSameClassAs(new String());
    }

    @Test
    @DisplayName("Replace returns a String when successful")
    void replace_ReturnsAStringWhenSuccessful() {

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        UserPutRequestBody userPutRequestBody = new UserPutRequestBody();
        userPutRequestBody.setName("KhalDrogo Velasquez");
        userPutRequestBody.setEmail("drogon@icloud.com");
        userPutRequestBody.setId(1L);
        String msg = userService.replace(userPutRequestBody);

        Assertions.assertThat(msg)
                .isNotNull()
                .isNotBlank()
                .hasSameClassAs(new String());
    }

}