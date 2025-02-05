package com.example.demo.users;

import com.example.demo.filtering.common.PageOptions;
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
    private final UserRepository userCriteriaRepository;

    @Autowired
    public UserService(UserRepository userCriteriaRepository) {
        this.userCriteriaRepository = userCriteriaRepository;
    }

    public List<UserEntity> getUsers(PageOptions pageOptions) {
        Pageable pageable = getPageable(pageOptions);
        return userCriteriaRepository.findAll(pageable).toList();
    }

    private Pageable getPageable(PageOptions pageOptions) {
        Sort sort = Sort.by(pageOptions.getSortDirection(), pageOptions.getSortBy());
        return PageRequest.of(pageOptions.getPageNumber(), pageOptions.getPageSize(), sort);
    }

    public List<UserEntity> getUsers(PageOptions pageOptions, Specification<UserEntity> spec) {
        Pageable pageable = getPageable(pageOptions);
        return userCriteriaRepository.findAll(spec, pageable).toList();
    }

    public UserEntity createUsers(UserDto user) {
        Optional<UserEntity> userByEmail = userCriteriaRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        return userCriteriaRepository
                .save(new UserEntity(null, user.getFirstName(), user.getLastName(), user.getEmail()));
    }
}
