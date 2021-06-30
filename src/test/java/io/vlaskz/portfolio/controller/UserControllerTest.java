package io.vlaskz.portfolio.controller;

import io.vlaskz.portfolio.model.User;
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

}