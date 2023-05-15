package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByUserName(String userName);
}
