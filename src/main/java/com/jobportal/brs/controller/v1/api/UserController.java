package com.jobportal.brs.controller.v1.api;

import com.jobportal.brs.controller.v1.request.UserSignupRequest;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.dto.response.Response;
import com.jobportal.brs.service.UserService;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Handles the incoming POST API "/v1/user/signup"
     *
     * @param userSignupRequest
     * @return
     * @throws Exception 
     */
    @PostMapping("/signup")
    public Response signup(@RequestBody @Valid UserSignupRequest userSignupRequest) throws Exception {
        return Response.ok().setPayload(registerUser(userSignupRequest, false));
    }

    /**
     * Register a new user in the database
     *
     * @param userSignupRequest
     * @return
     * @throws Exception 
     */
    private UserDto registerUser(UserSignupRequest userSignupRequest, boolean isAdmin) throws Exception {
        UserDto userDto = new UserDto()
                .setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword())
                .setFirstName(userSignupRequest.getFirstName())
                .setLastName(userSignupRequest.getLastName())
                .setMobileNumber(userSignupRequest.getMobileNumber())
                .setRef(userSignupRequest.getRef())
                .setAdmin(isAdmin);

        return userService.signup(userDto);
    }
}
