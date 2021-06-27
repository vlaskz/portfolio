package io.vlaskz.portfolio.service;

import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.repository.UserRepository;
import io.vlaskz.portfolio.request.UserPostRequestBody;
import io.vlaskz.portfolio.request.UserPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByIdOrThrowBadRequestException(long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException((HttpStatus.BAD_REQUEST), "User not found"));
    }

    public User save(UserPostRequestBody userPostRequestBody) {
       User user =  User.builder().name(userPostRequestBody.getName()).email(userPostRequestBody.getEmail()).build();
        return userRepository.save(user);
    }

    public String delete(Long id) {
        userRepository.delete(findByIdOrThrowBadRequestException(id));
        return "User with id " + id + " was dropped.";
    }

    public String replace(UserPutRequestBody userPutRequestBody) {

        findByIdOrThrowBadRequestException(userPutRequestBody.getId());

        User user = User.builder()
                .id(userPutRequestBody.getId())
                .name(userPutRequestBody.getName())
                .email(userPutRequestBody.getEmail())
                .build();

        userRepository.save(user);
        return "User id #" + user.getId() + " now belongs to " + user.getName() + ".";
    }
}
