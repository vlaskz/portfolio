package io.vlaskz.portfolio.controller;

import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.service.UserService;
import io.vlaskz.portfolio.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final DateUtil dateUtil;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        log.info(dateUtil.convertLocalDateTimeToServerFormal(LocalDateTime.now()) + " list");
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        log.info(dateUtil.convertLocalDateTimeToServerFormal(LocalDateTime.now()) + " findUserById");
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        log.info(dateUtil.convertLocalDateTimeToServerFormal(LocalDateTime.now()) + "save");
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info(dateUtil.convertLocalDateTimeToServerFormal(LocalDateTime.now()) + "delete");
        return new ResponseEntity<String>(userService.delete(id), HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping
    public ResponseEntity<String> replace(@RequestBody User user){
        log.info(dateUtil.convertLocalDateTimeToServerFormal(LocalDateTime.now()) + "delete");
        return new ResponseEntity<String>(userService.replace(user), HttpStatus.I_AM_A_TEAPOT);
    }

}
