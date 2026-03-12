package com.mohamed.securecaptia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID userId;

//    @NotEmpty(message = "First name is required")
    private String firstName;

//    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Password is required")
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private String address;
    private String phone;
    private String title;
    private String bio;
    private Boolean enabled;
    private Boolean nonLocked;
    private Boolean usingMfa;
    private LocalDateTime createdAt;
    private String imageUrl;
}