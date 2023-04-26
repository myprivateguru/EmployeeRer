package com.jobportal.brs.dto.mapper;

import com.jobportal.brs.dto.model.user.ReferralDto;
import com.jobportal.brs.dto.model.user.RoleDto;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.model.user.Referral;
import com.jobportal.brs.model.user.Role;
import com.jobportal.brs.model.user.User;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber())
                .setRoles(new HashSet<RoleDto>(user
                        .getRoles()
                        .stream()
                        .map(role -> new ModelMapper().map(role, RoleDto.class))
                        .collect(Collectors.toSet())));
    }
    
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setUsername(userDto.getUsername().toLowerCase());
        user.setCoins(userDto.getCoins());
        
   
        
        return user;
    }

}
