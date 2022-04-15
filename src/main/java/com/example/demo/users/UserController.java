package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public List<User> getUsers(PageOptions pageOptions, String search) {
        if (search != null) {
            CriteriaParser parser = new CriteriaParser();
            GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<User> spec = specBuilder.build(parser.parse(search), UserSpecification::new);
            return userService.getUsers(pageOptions, spec);
        }

        return userService.getUsers(pageOptions);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUsers(user), HttpStatus.OK);
    }
}
