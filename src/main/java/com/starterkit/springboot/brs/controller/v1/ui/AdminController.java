package com.starterkit.springboot.brs.controller.v1.ui;


import com.starterkit.springboot.brs.controller.v1.command.AdminSignupFormCommand;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.model.user.User;
import com.starterkit.springboot.brs.repository.user.UserRepository;
import com.starterkit.springboot.brs.service.JobsReservationService;
import com.starterkit.springboot.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Dnyaneshwar Somwanshi.
 */

@Controller
public class AdminController {
	private static final int COINS_PER_REFERRAL = 10;

    @Autowired
    JobsReservationService jobsReservationService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping(value = {"/logout"})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:login";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "redirect:dashboard";
    }

    @GetMapping(value = "/signup")
    public ModelAndView signup(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("adminSignupFormData", new AdminSignupFormCommand());
        //String ref = request.getParameter("ref"); // retrieve the value of the 'ref' parameter
	    // add the 'ref' value to the model
	    //model.addAttribute("ref", ref);
        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewAdmin(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand adminSignupFormCommand, BindingResult bindingResult,HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
            	//String ref = request.getParameter("ref");
            	
                UserDto newUser = registerAdmin(adminSignupFormCommand);
                userService.signup(newUser);
                
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.adminSignupFormCommand", exception.getMessage());
                return modelAndView;
            }
        }
        return new ModelAndView("login");
    }

    /**
     * Register a new user in the database
     *
     * @param adminSignupRequest
     * @return
     */
    private UserDto registerAdmin(@Valid AdminSignupFormCommand adminSignupRequest) {
        UserDto userDto = new UserDto()
                .setEmail(adminSignupRequest.getEmail())
                .setPassword(adminSignupRequest.getPassword())
                .setFirstName(adminSignupRequest.getFirstName())
                .setLastName(adminSignupRequest.getLastName())
                .setMobileNumber(adminSignupRequest.getMobileNumber())
                .setUsername(adminSignupRequest.getUsername())
                .setRef(adminSignupRequest.getRef())
                .setAdmin(true);
        
            User referrer = userRepository.findByEmail(userDto.getRef());
            if (referrer != null) {
                referrer.setCoins(referrer.getCoins() + COINS_PER_REFERRAL);
                userRepository.save(referrer);
                userDto.setCoins(COINS_PER_REFERRAL);
            }
        
       
        return userDto;
    }
}
