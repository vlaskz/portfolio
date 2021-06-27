package io.vlaskz.portfolio.controller;

import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.request.UserPostRequestBody;
import io.vlaskz.portfolio.request.UserPutRequestBody;
import io.vlaskz.portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<User>> findUserByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }


    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.save(userPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<String>(userService.delete(id), HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping
    public ResponseEntity<String> replace(@RequestBody UserPutRequestBody userPutRequestBody) {
        return new ResponseEntity<String>(userService.replace(userPutRequestBody), HttpStatus.I_AM_A_TEAPOT);
    }

}
