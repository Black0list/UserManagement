package com.spring.service;

import com.spring.dto.UserCreateDTO;
import com.spring.dto.UserResponseDTO;
import com.spring.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponseDTO create(UserCreateDTO dto);
    Page<UserResponseDTO> list(int page, int size, boolean onlyActive);
    UserResponseDTO get(Long id);
    UserResponseDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id, boolean hard);
}
