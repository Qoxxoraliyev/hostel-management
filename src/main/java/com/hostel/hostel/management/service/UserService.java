package com.hostel.hostel.management.service;

import com.hostel.hostel.management.config.Constants;
import com.hostel.hostel.management.entity.Authority;
import com.hostel.hostel.management.entity.User;
import com.hostel.hostel.management.exceptions.*;
import com.hostel.hostel.management.repository.AuthorityRepository;
import com.hostel.hostel.management.repository.UserRepository;
import com.hostel.hostel.management.security.SecurityUtils;
import com.hostel.hostel.management.service.dto.AdminUserDTO;
import com.hostel.hostel.management.service.dto.UserDTO;
import com.hostel.hostel.management.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthorityRepository authorityRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }


    public User registerUser(AdminUserDTO dto, String password) {
        userRepository.findOneByLogin(dto.getLogin().toLowerCase())
                .ifPresent(u -> { throw new UsernameAlreadyUsedException(); });

        userRepository.findOneByEmailIgnoreCase(dto.getEmail())
                .ifPresent(u -> { throw new EmailAlreadyUsedException(); });

        User user = new User();
        user.setLogin(dto.getLogin().toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setActivated(false);
        user.setActivationKey(RandomUtil.generateKey());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
        user.setAuthorities(authorities);

        userRepository.save(user);
        LOG.debug("Registered user: {}", user.getLogin());
        return user;
    }


    public Optional<User> activateRegistration(String key) {
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    return user;
                });
    }


    public Optional<User> requestPasswordReset(String email) {
        return userRepository.findOneByEmailIgnoreCase(email)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateKey());
                    user.setResetDate(Instant.now());
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        return userRepository.findOneByResetKey(key)
                .filter(user ->
                        user.getResetDate().isAfter(
                                Instant.now().minus(1, ChronoUnit.DAYS)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    return user;
                });
    }


    public User createUser(AdminUserDTO dto) {
        User user = new User();
        user.setLogin(dto.getLogin().toLowerCase());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode(RandomUtil.generatePassword()));

        Set<Authority> authorities = dto.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        user.setAuthorities(authorities);
        return userRepository.save(user);
    }


    public Optional<AdminUserDTO> updateUser(AdminUserDTO dto) {
        return userRepository.findById(dto.getId())
                .map(user -> {
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    user.setEmail(dto.getEmail());
                    user.setActivated(dto.isActivated());

                    Set<Authority> authorities = dto.getAuthorities().stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toSet());

                    user.setAuthorities(authorities);
                    return user;
                })
                .map(AdminUserDTO::new);
    }


    public void changePassword(String currentPassword, String newPassword) {
        User user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .orElseThrow(InvalidPasswordException::new);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login)
                .ifPresent(userRepository::delete);
    }


    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable)
                .map(UserDTO::new);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
                        Instant.now().minus(3, ChronoUnit.DAYS)
                )
                .forEach(userRepository::delete);
    }
}
