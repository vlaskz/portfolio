package io.vlaskz.portfolio.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPostRequestBody {
    @NotNull(message="User name cannot be null")
    @NotEmpty(message = "User name cannot be empty")
    private String name;
    private String email;

}
