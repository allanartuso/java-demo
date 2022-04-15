package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Deque;
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
    public List<User> getUsers(PageOptions pageOptions, SearchCriteria searchCriteria){
        return  userService.getUsers(pageOptions, searchCriteria);
    }

    @GetMapping(value = "/adv")
    @ResponseBody
    public Deque<?> findAllByAdvPredicate(@RequestParam(value = "search") String search) {
        CriteriaParser parser = new CriteriaParser();
        return parser.parse(search);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>( userService.createUsers(user), HttpStatus.OK);
    }
}
