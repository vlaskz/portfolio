package io.vlaskz.portfolio.request;

import lombok.Data;

@Data
public class UserPutRequestBody {
    private Long id;
    private String name;
    private String email;
}
