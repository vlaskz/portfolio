package io.vlaskz.portfolio.client;

import io.vlaskz.portfolio.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {


        ResponseEntity<List<User>> users = new RestTemplate().exchange("http://localhost:8080/users/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                });

        log.info(users);
    }
}
