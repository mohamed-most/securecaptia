package com.mohamed.securecaptia.mapper;

import com.mohamed.securecaptia.domain.User;
import com.mohamed.securecaptia.dto.UserDto;
import org.springframework.beans.BeanUtils;


public class UserDtoMapper {
    public static User toUser(UserDto userDto){
        User user = new User( ) ;
        BeanUtils.copyProperties(userDto , user);
        return user ;
    }

    public  static  UserDto toDto(User user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user , userDto);
        return userDto ;
    }

}
