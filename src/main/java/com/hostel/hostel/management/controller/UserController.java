package com.hostel.hostel.management.controller;
import com.hostel.hostel.management.config.Constants;
import com.hostel.hostel.management.entity.User;
import com.hostel.hostel.management.exceptions.EmailAlreadyUsedException;
import com.hostel.hostel.management.exceptions.LoginAlreadyUsedException;
import com.hostel.hostel.management.repository.UserRepository;
import com.hostel.hostel.management.security.AuthoritiesConstants;
import com.hostel.hostel.management.service.MailService;
import com.hostel.hostel.management.service.UserService;
import com.hostel.hostel.management.service.dto.AdminUserDTO;
import com.hostel.hostel.management.util.PaginationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = List.of(
            "id",
            "login",
            "firstName",
            "lastName",
            "email",
            "activated",
            "langKey",
            "createdDate",
            "lastModifiedDate"
    );

    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;

    public UserController(
            UserService userService,
            UserRepository userRepository,
            MailService mailService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }



    @PostMapping("/users")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<User> createUser(@Valid @RequestBody AdminUserDTO userDTO) throws URISyntaxException {
        LOG.debug("REST request to create User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new IllegalArgumentException("New user cannot already have an ID");
        }

        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }

        if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        User newUser = userService.createUser(userDTO);
        mailService.sendCreationEmail(newUser);

        return ResponseEntity
                .created(new URI("/api/admin/users/" + newUser.getLogin()))
                .body(newUser);
    }



    @PutMapping("/users/{login}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<AdminUserDTO> updateUser(
            @PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login,
            @Valid @RequestBody AdminUserDTO userDTO
    ) {
        LOG.debug("REST request to update User : {}", userDTO);

        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail())
                .filter(u -> !u.getId().equals(userDTO.getId()))
                .ifPresent(u -> { throw new EmailAlreadyUsedException(); });

        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase())
                .filter(u -> !u.getId().equals(userDTO.getId()))
                .ifPresent(u -> { throw new LoginAlreadyUsedException(); });

        return userService.updateUser(userDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/users")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(
            @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get all users (admin)");

        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        Page<AdminUserDTO> page = userService.getAllUsers(pageable);
        HttpHeaders headers =
                PaginationUtil.generatePaginationHttpHeaders(
                        ServletUriComponentsBuilder.fromCurrentRequest(),
                        page
                );

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



    @GetMapping("/users/{login}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<AdminUserDTO> getUser(
            @PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login
    ) {
        LOG.debug("REST request to get User : {}", login);

        return userService.getUserWithAuthoritiesByLogin(login)
                .map(AdminUserDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login
    ) {
        LOG.debug("REST request to delete User : {}", login);
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }


    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream()
                .map(Sort.Order::getProperty)
                .allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
