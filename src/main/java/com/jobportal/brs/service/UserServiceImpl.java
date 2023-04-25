package com.jobportal.brs.service;

import com.jobportal.brs.dto.mapper.UserMapper;
import com.jobportal.brs.dto.model.user.ProfileCompletionDto;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.exception.BRSException;
import com.jobportal.brs.exception.EntityType;
import com.jobportal.brs.exception.ExceptionType;
import com.jobportal.brs.model.user.Role;
import com.jobportal.brs.model.user.User;
import com.jobportal.brs.model.user.UserRoles;
import com.jobportal.brs.repository.user.RoleRepository;
import com.jobportal.brs.repository.user.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.jobportal.brs.exception.EntityType.USER;
import static com.jobportal.brs.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.jobportal.brs.exception.ExceptionType.ENTITY_NOT_FOUND;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobsReservationService busReservationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        Role userRole;
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = roleRepository.findByRole(UserRoles.ADMIN);
            } else {
                userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            }
            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(new HashSet<>(Arrays.asList(userRole)))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setCoins(userDto.getCoins())
                    .setUsername(userDto.getUsername())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    /**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }
    
    public ProfileCompletionDto getProfileCompletion(UserDto user) {
    	//Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
    	ProfileCompletionDto profileDto= new ProfileCompletionDto();
        int totalFields = 0;
        int completedFields = 0;
        ArrayList<Field>uncompletedFields= new ArrayList<>();
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            totalFields++;
            field.setAccessible(true);
            try {
                if (field.get(user) != null && !field.get(user).toString().isEmpty()) {
                    completedFields++;
                }else
                {
                	uncompletedFields.add(field);
                	
                }
            } catch (IllegalAccessException e) {
                // handle exception
            }
        }
        profileDto.setProfileCompleted((int) Math.round((double) completedFields / totalFields * 100));
     profileDto.setUncompletedFields(uncompletedFields);
         return profileDto;
    }

	@Override
	public User FindByusername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}


}
