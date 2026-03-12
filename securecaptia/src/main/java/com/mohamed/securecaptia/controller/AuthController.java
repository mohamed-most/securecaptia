package com.mohamed.securecaptia.controller;

import com.mohamed.securecaptia.dto.ApiResponse;
import com.mohamed.securecaptia.dto.UserDto;
import com.mohamed.securecaptia.repository.UserRepository;
import com.mohamed.securecaptia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>>registerApi(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }
}
