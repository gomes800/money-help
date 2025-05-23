package com.gom.money_help.repositories;

import com.gom.money_help.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);
}
