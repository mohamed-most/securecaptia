package com.mohamed.securecaptia.service.imp;

import com.mohamed.securecaptia.domain.User;
import com.mohamed.securecaptia.dto.ApiResponse;
import com.mohamed.securecaptia.dto.UserDto;
import com.mohamed.securecaptia.exception.BadRequestException;
import com.mohamed.securecaptia.mapper.UserDtoMapper;
import com.mohamed.securecaptia.repository.UserRepository;
import com.mohamed.securecaptia.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    @Override
    public ApiResponse<UserDto> createUser(final UserDto userDto) {

        log.info("Registering user with email: {}", userDto.getEmail());


        //1)find user with this email if found return error
        if( userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new BadRequestException("This Email is already in use ");
        }

        //2) save user to database
        // TODO not forgot to hash after add spring security
        UserDto hashedUserDto = new UserDto();
        BeanUtils.copyProperties(userDto , hashedUserDto);
        hashedUserDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = UserDtoMapper.toUser(hashedUserDto);
        user.setEnabled(false);
        user.setNonLocked(true);
        user.setUsingMfa(false);
        user.setCreatedAt(Instant.now());


        User savedUser = userRepository.save(user);

        //3)return user to client as following api stander
        return ApiResponse.<UserDto>builder()
                .status(HttpStatus.CREATED.value())
                .msg("User Created Successfully")
                .data(UserDtoMapper.toDto(savedUser))
                .build();
    }
}
