package com.example.demo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    //    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<UserEntity> findUserByEmail(String email);
}
