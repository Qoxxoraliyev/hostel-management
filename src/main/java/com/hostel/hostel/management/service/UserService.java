package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.User;
import com.hostel.hostel.management.service.dto.AdminUserDTO;
import com.hostel.hostel.management.service.dto.UserDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserWithAuthorities();

    User registerUser(AdminUserDTO dto, String password);

    Optional<User> getUserWithAuthoritiesByLogin(String login);

    Optional<User> activateRegistration(String key);

    Optional<User> requestPasswordReset(String email);

    Optional<User> completePasswordReset(String newPassword, String key);

    User createUser(AdminUserDTO dto);

    Optional<AdminUserDTO> updateUser(AdminUserDTO dto);

    void changePassword(String currentPassword, String newPassword);

    void deleteUser(String login);

    Page<AdminUserDTO> getAllUsers(Pageable pageable);

    Page<UserDTO> getPublicUsers(Pageable pageable);

    void removeNotActivatedUsers();

}
