package com.example.demo.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {
    private Long id;
    @NotNull(message="First name cannot be null")
    private String firstName;
    private String lastName;

    @NotNull
    @Email
    private String email;
}
