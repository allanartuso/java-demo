package com.example.demo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> , JpaSpecificationExecutor<User> {

//    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);
}
