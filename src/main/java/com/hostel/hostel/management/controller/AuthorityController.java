package com.hostel.hostel.management.controller;

import com.hostel.hostel.management.entity.Authority;
import com.hostel.hostel.management.repository.AuthorityRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorityController.class);

    private final AuthorityRepository authorityRepository;

    public AuthorityController(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Authority> createAuthority(@Valid @RequestBody Authority authority) throws URISyntaxException {
        LOG.debug("Request to create Authority: {}", authority);

        if (authorityRepository.existsById(authority.getName())) {
            return ResponseEntity.badRequest().body(null);
        }

        Authority savedAuthority = authorityRepository.save(authority);
        return ResponseEntity
                .created(new URI("/api/authorities/" + savedAuthority.getName()))
                .body(savedAuthority);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Authority> getAllAuthorities() {
        LOG.debug("Request to get all Authorities");
        return authorityRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Authority> getAuthority(@PathVariable String id) {
        LOG.debug("Request to get Authority: {}", id);
        Optional<Authority> authority = authorityRepository.findById(id);
        return authority.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthority(@PathVariable String id) {
        LOG.debug("Request to delete Authority: {}", id);
        if (authorityRepository.existsById(id)) {
            authorityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
