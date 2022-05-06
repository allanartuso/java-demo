package com.example.demo.users;

import com.example.demo.users.common.PageOptions;
import com.example.demo.users.composite.CompositeGenericSpecification;
import com.example.demo.users.composite.CompositeGenericSpecificationsBuilder;
import com.example.demo.users.composite.CompositeQueryOptions;
import com.example.demo.users.original.CriteriaParser;
import com.example.demo.users.original.GenericSpecification;
import com.example.demo.users.original.GenericSpecificationsBuilder;
import com.example.demo.users.original.QueryOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ResponseBody
    public List<UserEntity> getUsers(PageOptions pageOptions,
                                     @RequestParam(value = "search", required = false) String search) {
        if (search != null) {
            CriteriaParser parser = new CriteriaParser();
            GenericSpecificationsBuilder<UserEntity> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<UserEntity> spec = specBuilder.build(parser.parse(search), GenericSpecification::new);
            return userService.getUsers(pageOptions, spec);
        }

        return userService.getUsers(pageOptions);
    }

    @GetMapping("/parsed")
    @ResponseBody
    public List<Object> getParsed(@RequestParam(value = "search", required = false) String search) {
        CriteriaParser parser = new CriteriaParser();
        return parser.parse(search);
    }

    @PostMapping("/query")
    public List<UserEntity> queryUsers(@RequestBody QueryOptions queryOptions) {
        if (queryOptions.search != null) {
            GenericSpecificationsBuilder<UserEntity> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<UserEntity> spec = specBuilder.build(queryOptions.search,
                    GenericSpecification::new);
            return userService.getUsers(queryOptions.pageOptions, spec);
        }

        return userService.getUsers(queryOptions.pageOptions);
    }

    @PostMapping("/composite")
    public List<UserEntity> queryCompositeUsers(@RequestBody CompositeQueryOptions compositeQueryOptions) {
        if (compositeQueryOptions.search != null) {
            CompositeGenericSpecificationsBuilder<UserEntity> specBuilder = new CompositeGenericSpecificationsBuilder<>();
            Specification<UserEntity> spec = specBuilder
                    .build(compositeQueryOptions.search, CompositeGenericSpecification::new);
            return userService.getUsers(compositeQueryOptions.pageOptions, spec);
        }

        return userService.getUsers(compositeQueryOptions.pageOptions);
    }

    @GetMapping("/mixed")
    public Map<String, String> mixedUsers(PageOptions pageOptions, FilterFields filterFields) {
        Map<?,?> gfg =  this.objectMapper.convertValue(filterFields, Map.class);
        Map<String, String> map =  new HashMap<>();

        for (Map.Entry<?,?> entry : gfg.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key.toString());

            if(key instanceof String && (value instanceof String || value instanceof Integer)){
                map.put((String) key,value.toString());
            }else{
                throw new IllegalArgumentException("Key must be a string and value must be a String or Integer");
            }


        }

        return map;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(userService.createUsers(user), HttpStatus.OK);
    }
}
