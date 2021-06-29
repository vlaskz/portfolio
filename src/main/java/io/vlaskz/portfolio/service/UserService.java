package io.vlaskz.portfolio.service;

import io.vlaskz.portfolio.exception.BadRequestException;
import io.vlaskz.portfolio.mapper.UserMapper;
import io.vlaskz.portfolio.model.User;
import io.vlaskz.portfolio.repository.UserRepository;
import io.vlaskz.portfolio.request.UserPostRequestBody;
import io.vlaskz.portfolio.request.UserPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findByIdOrThrowBadRequestException(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User save(UserPostRequestBody userPostRequestBody) {
        return userRepository.save(UserMapper.INSTANCE.toUser(userPostRequestBody));
    }

    public String delete(Long id) {
        userRepository.delete(findByIdOrThrowBadRequestException(id));
        return "User with id " + id + " was dropped.";
    }

    public String replace(UserPutRequestBody userPutRequestBody) {

        User savedUser = findByIdOrThrowBadRequestException(userPutRequestBody.getId());
        User user = UserMapper.INSTANCE.toUser(userPutRequestBody);
        user.setId(savedUser.getId());
        userRepository.save(user);
        return "User id #" + user.getId() + " now belongs to " + user.getName() + ".";
    }
}
