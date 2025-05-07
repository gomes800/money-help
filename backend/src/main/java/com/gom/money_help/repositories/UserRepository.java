package com.gom.money_help.repositories;

import com.gom.money_help.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
