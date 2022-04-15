package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserCriteriaRepository userCriteriaRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserCriteriaRepository userCriteriaRepository) {
        this.userRepository = userRepository;
        this.userCriteriaRepository = userCriteriaRepository;
    }

    public List<User> getUsers(PageOptions pageOptions) {
        Sort sort = Sort.by(pageOptions.getSortDirection(), pageOptions.getSortBy());
        Pageable pageable = PageRequest.of(pageOptions.getPageNumber(), pageOptions.getPageSize(), sort);
        return userRepository.findAll(pageable).toList();
    }

    public List<User> getUsers(PageOptions pageOptions, Specification<User> spec) {
        Sort sort = Sort.by(pageOptions.getSortDirection(), pageOptions.getSortBy());
        Pageable pageable = PageRequest.of(pageOptions.getPageNumber(), pageOptions.getPageSize(), sort);
        return userCriteriaRepository.findAll(spec);
    }

    public User createUsers(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        return userRepository.save(user);
    }
}
