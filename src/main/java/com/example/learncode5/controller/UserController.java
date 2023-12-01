package com.example.learncode5.controller;

import com.example.learncode5.DTO.UserDTO;
import com.example.learncode5.payload.Response.ResponseObject;
import com.example.learncode5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")

public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
//    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getUserList() {
        return userService.getUserList();
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id){
        return userService.deleteUserById(id);
    }
    @GetMapping("/{username}")
//    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
    @PostMapping("/{id}")
//    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @RequestBody UserDTO userDTO){
        return userService.updateUser(id,userDTO.getFullName(),userDTO.getEmail(),userDTO.getRoles());
    }
}
