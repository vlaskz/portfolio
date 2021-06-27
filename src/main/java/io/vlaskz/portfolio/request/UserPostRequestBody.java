package io.vlaskz.portfolio.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data

public class UserPostRequestBody {
    @NotNull(message="User name cannot be null")
    @NotEmpty(message = "User name cannot be empty")
    private String name;
    private String email;

}
