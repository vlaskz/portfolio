package io.vlaskz.portfolio.util;

import io.vlaskz.portfolio.model.User;

public class UserCreator {

    public static User createUserToBeSaved() {
        return User.builder()
                .name("Skyrim Velasquez")
                .email("fusrodah@icloud.com")
                .build();
    }

    public static User createValidUser() {
        return User.builder()
                .id(1L)
                .name("Paarthurnax Velasquez")
                .email("fusrodah@icloud.com")
                .build();
    }

    public static User createValidUpdatedUser() {
        return User.builder()
                .id(1L)
                .name("Alduin Velasquez")
                .email("fusrodah@icloud.com")
                .build();
    }
}
