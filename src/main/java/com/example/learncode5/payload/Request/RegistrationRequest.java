package com.example.learncode5.payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private Set<String> roles;
}
