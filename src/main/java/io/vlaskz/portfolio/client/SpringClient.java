package io.vlaskz.portfolio.client;

import io.vlaskz.portfolio.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

//        User[] users  = new RestTemplate().getForObject("http://localhost:8080/users/all", User[].class);
//
//        log.info(Arrays.toString(users));
//
//        ResponseEntity<List<User>> exchange = new RestTemplate().exchange("http://localhost:8080/users/all",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {});
//
//        log.info(exchange);
//
//        User brainiac = User.builder().name("Brainiac Velasquez").email("suppamanisded@icloud.com").build();
//        User savedBrainiac = new RestTemplate().postForObject("http://localhost:8080/users", brainiac, User.class);
//
//        log.info("savedBrainiac {}", savedBrainiac);


        User chtulhu = User.builder().name("Chtulhu").email("devourerofworlds@icloud.com").build();
        ResponseEntity<User> savedChtulhu = new RestTemplate().exchange("http://localhost:8080/users",
                HttpMethod.POST,
                new HttpEntity<>(chtulhu, createJsonHeader()),
                User.class);

        log.info(savedChtulhu);

        User userSaved = savedChtulhu.getBody();
        userSaved.setName("Bowser Van Koopa");

        ResponseEntity<Void> userSavedUpdated = new RestTemplate().exchange("http://localhost:8080/users",
                HttpMethod.PUT,
                new HttpEntity<>(userSaved, createJsonHeader()),
                Void.class);

        log.info(userSavedUpdated);




        ResponseEntity<Void> userDeleted =
                new RestTemplate().exchange("http://localhost:8080/users/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                userSaved.getId());

        log.info(userDeleted);

    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
