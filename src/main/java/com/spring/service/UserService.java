package com.spring.service;

import com.spring.dto.UserCreateDTO;
import com.spring.dto.UserResponseDTO;
import com.spring.dto.UserUpdateDTO;
import com.spring.entity.User;
import com.spring.mapper.UserMapper;
import com.spring.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponseDTO create(UserCreateDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User u = UserMapper.toEntity(dto);
        u = repo.save(u);
        return UserMapper.toResponse(u);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> list() {
        List<User> users = repo.findAll();
        return users.stream().map(UserMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO get(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserMapper.toResponse(u);
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(u.getEmail())
                && repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        UserMapper.patch(u, dto);
        u = repo.save(u);
        return UserMapper.toResponse(u);
    }

    public void delete(Long id, boolean hard) {
        User u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (hard) {
            repo.delete(u);
        } else {
            u.setActive(false);
            repo.save(u);
        }
    }

    public boolean login(String email, String password) {
        Optional<User> u = repo.findByEmail(email);
        if(u.isEmpty()) throw new IllegalArgumentException("email not found");

        if(!u.get().getPassword().equals(password)) throw new IllegalArgumentException("passwords dont match");

        return true;
    }
}