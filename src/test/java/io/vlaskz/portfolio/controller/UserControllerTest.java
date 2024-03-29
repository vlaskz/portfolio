package io.vlaskz.portfolio.controller;

import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.request.UserPostRequestBody;
import io.vlaskz.portfolio.request.UserPutRequestBody;
import io.vlaskz.portfolio.service.UserService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setup() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createValidUser()));
        BDDMockito.when(userServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(userPage);

        BDDMockito.when(userServiceMock.listAll())
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.delete(ArgumentMatchers.anyLong()))
                .thenReturn("User has been dropped.");

        BDDMockito.when(userServiceMock.replace(ArgumentMatchers.any(UserPutRequestBody.class)))
                .thenReturn("User has been replaced");

    }


    @Test
    @DisplayName("List return list of Users inside page when successful")
    void list_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {
        String expectedName = UserCreator.createValidUser().getName();

        Page<User> userPage = userController.list(null).getBody();

        Assertions.assertThat(userPage).isNotNull();
        Assertions.assertThat(userPage.toList()).isNotEmpty();
        Assertions.assertThat(userPage.toList()
                .get(0)
                .getName())
                .isNotNull()
                .isEqualTo(expectedName);

    }


    @Test
    @DisplayName("ListAll return list of Users inside page when successful")
    void listAll_ReturnsListOfUsersInsidePageObjectWhenSuccessful() {
        String expectedName = UserCreator.createValidUser().getName();
        List<User> userList = userController.listAll().getBody();


        Assertions.assertThat(userList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);


    }

    @Test
    @DisplayName("FindById returns a User when successful")
    void findById_ReturnsUserWhenSuccessful() {
        Long userId = UserCreator.createValidUser().getId();

        User user = userController.findUserById(userId).getBody();
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(userId);

    }


    @Test
    @DisplayName("FindByName returns a list of Users  when successful")
    void findByName_ReturnsListOfUsersWhenSuccessful() {

        List<User> userList = userController.findUserByName("name").getBody();

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


        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> userList = userController.findUserByName("name").getBody();
        Assertions.assertThat(userList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save returns a User when successful")
    void save_ReturnsUserWhenSuccessful() {

        User user = userController.save(new UserPostRequestBody()).getBody();

        Assertions.assertThat(user)
                .isNotNull();
    }

    @Test
    @DisplayName("Delete returns a String when successful")
    void delete_ReturnsAStringWhenSuccessful() {

        String msg = userController.delete(1L).getBody();

        Assertions.assertThat(msg)
                .isNotNull()
                .isNotBlank()
                .hasSameClassAs(new String());
    }

    @Test
    @DisplayName("Replace returns a String when successful")
    void replace_ReturnsAStringWhenSuccessful() {
        UserPutRequestBody user = new UserPutRequestBody();
        user.setName("KhalDrogo Velasquez");
        user.setEmail("drogon@icloud.com");
        String msg = userController.replace(user).getBody();

        Assertions.assertThat(msg)
                .isNotNull()
                .isNotBlank()
                .hasSameClassAs(new String());
    }

}