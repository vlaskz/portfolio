package io.vlaskz.portfolio.client;

import io.vlaskz.portfolio.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<User> entity = new RestTemplate().getForEntity("http://localhost:8080/users/{id}",
                User.class, 90,91,92,93);
        User user  = new RestTemplate().getForObject("http://localhost:8080/users/{id}", User.class,
                30,31,32,33);
        log.info(entity);
        log.info(user);
    }
}
