package com.example.demo.users;

import com.example.demo.filtering.common.PageOptions;
import com.example.demo.filtering.composite.CompositeGenericSpecification;
import com.example.demo.filtering.composite.CompositeGenericSpecificationsBuilder;
import com.example.demo.filtering.composite.CompositeQueryOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ResponseBody
    public List<UserEntity> getUsers(PageOptions pageOptions, FilterFields filterFields) {
        kafkaTemplate.send("myTopic", "Get users");

        Map<?,?> gfg =  this.objectMapper.convertValue(filterFields, Map.class);
        Map<String, String> map =  new HashMap<>();

        for (Map.Entry<?,?> entry : gfg.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            if(value == null){
                continue;
            }

            if(key instanceof String && (value instanceof String || value instanceof Integer)){
                map.put((String) key,value.toString());
            }else{
                throw new IllegalArgumentException("Key must be a string and value must be a String or Integer");
            }


        }

        return userService.getUsers(pageOptions);
    }

    @PostMapping("/search")
    public List<UserEntity> queryCompositeUsers(@RequestBody CompositeQueryOptions compositeQueryOptions) {
        if (compositeQueryOptions.search != null) {
            CompositeGenericSpecificationsBuilder<UserEntity> specBuilder = new CompositeGenericSpecificationsBuilder<>();
            Specification<UserEntity> spec = specBuilder
                    .build(compositeQueryOptions.search, CompositeGenericSpecification::new);
            return userService.getUsers(compositeQueryOptions.pageOptions, spec);
        }

        return userService.getUsers(compositeQueryOptions.pageOptions);
    }


    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(userService.createUsers(user), HttpStatus.OK);
    }
}
