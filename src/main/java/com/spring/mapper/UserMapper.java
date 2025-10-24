package com.spring.mapper;

import com.spring.dto.UserCreateDTO;
import com.spring.dto.UserResponseDTO;
import com.spring.dto.UserUpdateDTO;
import com.spring.entity.User;

public class UserMapper {

    public static User toEntity(UserCreateDTO dto) {
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setRole(dto.getRole());
        u.setActive(true);

        return u;
    }

    public static void patch(User u, UserUpdateDTO dto) {
        if (dto.getName() != null) u.setName(dto.getName());
        if (dto.getEmail() != null) u.setEmail(dto.getEmail());
        if (dto.getPassword() != null) u.setPassword(dto.getPassword());
        if (dto.getRole() != null) u.setRole(dto.getRole());
        if (dto.getActive() != null) u.setActive(dto.getActive());
    }

    public static UserResponseDTO toResponse(User u) {
        UserResponseDTO r = new UserResponseDTO();
        r.setId(u.getId());
        r.setName(u.getName());
        r.setEmail(u.getEmail());
        r.setRole(u.getRole());
        r.setActive(u.getActive());
        r.setCreated_at(u.getCreatedAt());
        return r;
    }
}