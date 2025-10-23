package com.spring.service.impl;

import com.spring.dto.UserCreateDTO;
import com.spring.dto.UserResponseDTO;
import com.spring.dto.UserUpdateDTO;
import com.spring.entity.User;
import com.spring.exception.ConflictException;
import com.spring.exception.NotFoundException;
import com.spring.mapper.UserMapper;
import com.spring.repository.UserRepository;
import com.spring.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserResponseDTO create(UserCreateDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        User u = UserMapper.toEntity(dto);
        u = repo.save(u);
        return UserMapper.toResponse(u);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> list(int page, int size, boolean onlyActive) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1), 100), Sort.by("id").descending());
        Page<User> result = onlyActive ? repo.findByActiveTrue(pageable) : repo.findAll(pageable);
        return new PageImpl<>(
                result.getContent().stream().map(UserMapper::toResponse).collect(Collectors.toList()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO get(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.toResponse(u);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(u.getEmail())
                && repo.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        UserMapper.patch(u, dto);
        u = repo.save(u);
        return UserMapper.toResponse(u);
    }

    @Override
    public void delete(Long id, boolean hard) {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (hard) {
            repo.delete(u);
        } else {
            u.setActive(false);
            repo.save(u);
        }
    }
}
