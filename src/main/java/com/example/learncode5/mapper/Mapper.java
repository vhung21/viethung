package com.example.learncode5.mapper;

import com.example.learncode5.DTO.RoleDTO;
import com.example.learncode5.DTO.UserDTO;
import com.example.learncode5.entities.Role;
import com.example.learncode5.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }
    public RoleDTO roleToRoleDTO(Role role) {
        return new RoleDTO(role);
    }

    public User userDtoToUser(UserDTO userDTO){
        User user =new User();
        user.setFullName(user.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
