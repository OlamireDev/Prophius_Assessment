package com.olamireDev.prophiusapi.repository;

import com.olamireDev.prophiusapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
