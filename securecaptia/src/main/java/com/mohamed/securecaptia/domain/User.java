package com.mohamed.securecaptia.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Entity
@Table(name = "users", schema = "securecapita")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @NotEmpty(message = "First name cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String address;
    private String phone;
    private String title;
    private String bio;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "non_locked")
    private Boolean nonLocked;

    @Column(name = "using_mfa")
    private Boolean usingMfa;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}