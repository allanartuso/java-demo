package com.example.demo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCriteriaRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
