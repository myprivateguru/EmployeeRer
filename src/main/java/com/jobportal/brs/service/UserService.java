package com.jobportal.brs.service;

import com.jobportal.brs.dto.model.user.ProfileCompletionDto;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.model.user.User;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface UserService {
    /**
     * Register a new user
     *
     * @param userDto
     * @return
     * @throws Exception 
     */
    UserDto signup(UserDto userDto) throws Exception;

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    UserDto findUserByEmail(String email);

    /**
     * Update profile of the user
     *
     * @param userDto
     * @return
     */
    UserDto updateProfile(UserDto userDto);

    /**
     * Update password
     *
     * @param newPassword
     * @return
     */
    UserDto changePassword(UserDto userDto, String newPassword);
    
    ProfileCompletionDto getProfileCompletion(UserDto userDto);

	User FindByusername(String username);
	
	void SetLoginHistory();
}
