package com.example.learncode5.service;

import com.example.learncode5.DTO.UserDTO;
import com.example.learncode5.entities.Role;
import com.example.learncode5.entities.User;
import com.example.learncode5.mapper.Mapper;
import com.example.learncode5.payload.Response.ResponseObject;
import com.example.learncode5.repository.RoleRespository;
import com.example.learncode5.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    RoleRespository roleRepository;

    public ResponseEntity<ResponseObject> getUserList() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(mapper.userToUserDTO(user));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("thanh cong", "", userDTOList));
    }

    public ResponseEntity<ResponseObject> deleteUserById(long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent() == false) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", "id not exist", id));
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok(new ResponseObject("success", "deleted successfully", mapper.userToUserDTO(user.get())));

    }

    public ResponseEntity<ResponseObject> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() == false) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", "username not exist", username));
        }
        return ResponseEntity.ok(new ResponseObject("success", "get user successfully", mapper.userToUserDTO(user.get())));
    }

    public ResponseEntity<ResponseObject> updateUser(long id, String fullName, String email, List<String> strRole) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent() == false) {
            return ResponseEntity.badRequest().body(
                    new ResponseObject("failed", "id not exists", null)
            );
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Email was exists", null));
        }
        User u = user.get();
        u.setFullName(fullName);
        u.setEmail(email);

        List<Role> roles = new ArrayList<>();
        if (strRole.size() == 0) {
            roles.add(roleRepository.findByName(Role.USER)
                    .orElseThrow(() -> new RuntimeException("user role not found")));
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleRepository.findByName(Role.ADMIN)
                                .orElseThrow(() -> new RuntimeException("admin role not found")));
                        break;
                    default:
                        roles.add(roleRepository.findByName(Role.USER)
                                .orElseThrow(() -> new RuntimeException("user role not found")));
                }
            });
        }
        u.setRoles(roles);
        userRepository.save(u);
        return ResponseEntity.ok(new ResponseObject("success", "", mapper.userToUserDTO(u)));
    }
}
