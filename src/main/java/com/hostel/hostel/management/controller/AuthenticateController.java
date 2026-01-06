package com.hostel.hostel.management.controller;

import com.hostel.hostel.management.config.SecurityJwtConfiguration;
import com.hostel.hostel.management.security.DomainUserDetailsService;
import com.hostel.hostel.management.controller.vm.LoginVM;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateController.class);
    private static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final SecurityJwtConfiguration jwtConfig;

    public AuthenticateController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, SecurityJwtConfiguration jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = createToken(authentication, loginVM.isRememberMe());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Void> isAuthenticated(Principal principal) {
        LOG.debug("REST request to check if the current user is authenticated");
        return ResponseEntity.status(principal == null ? HttpStatus.UNAUTHORIZED : HttpStatus.NO_CONTENT).build();
    }

    private String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity = rememberMe
                ? now.plus(jwtConfig.getTokenValiditySecondsForRememberMe(), ChronoUnit.SECONDS)
                : now.plus(jwtConfig.getTokenValiditySeconds(), ChronoUnit.SECONDS);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("auth", authorities);

        if (authentication.getPrincipal() instanceof DomainUserDetailsService.UserWithId user) {
            claimsBuilder.claim("user_id", user.getId());
        }

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsBuilder.build())).getTokenValue();
    }

    static class JWTToken {
        private String idToken;

        public JWTToken(String idToken) { this.idToken = idToken; }

        public String getIdToken() { return idToken; }

        public void setIdToken(String idToken) { this.idToken = idToken; }
    }
}
