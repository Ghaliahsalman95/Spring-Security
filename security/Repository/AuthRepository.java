package com.example.security.Repository;

import com.example.security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
}
