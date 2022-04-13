package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public List<User> getUsers(PageOptions pageOptions){
//        Sort sort = Sort.by(pageOptions.getSortDirection(), pageOptions.getSortBy());
//        Pageable pageable = PageRequest.of(pageOptions.getPageNumber(), pageOptions.getPageSize(), sort);
//
//        return userRepository.findAll(pageable).toList();
////        return List.of(new User(
////                1L,
////                "Mariam",
////                "Jamal",
////                "mariam.jamal@gmail.com"
////        ));
//    }

    public List<User> findAllByAdvPredicate(String search) {
        Specification<User> spec = resolveSpecificationFromInfixExpr(search);
        return userRepository.findAll(spec);
    }

    protected Specification<User> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), UserSpecification::new);
    }

    public User createUsers(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
         if(userByEmail.isPresent()){
             throw new IllegalStateException("email taken");
         }

        return userRepository.save(user);
    }
}
