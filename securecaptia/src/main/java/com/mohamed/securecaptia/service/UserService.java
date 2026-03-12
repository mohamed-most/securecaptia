package com.mohamed.securecaptia.service;

import com.mohamed.securecaptia.domain.User;
import com.mohamed.securecaptia.dto.ApiResponse;
import com.mohamed.securecaptia.dto.UserDto;

public interface UserService {
    ApiResponse<UserDto> createUser(UserDto user);
}
