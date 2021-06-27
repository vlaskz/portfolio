package io.vlaskz.portfolio.repository;


import io.vlaskz.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
