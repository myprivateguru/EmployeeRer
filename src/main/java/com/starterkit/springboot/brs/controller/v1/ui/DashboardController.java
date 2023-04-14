package com.starterkit.springboot.brs.controller.v1.ui;

import com.starterkit.springboot.brs.controller.v1.command.*;
import com.starterkit.springboot.brs.dto.model.user.JobsDto;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.model.user.Jobs;
import com.starterkit.springboot.brs.repository.user.JobsRepository;
import com.starterkit.springboot.brs.service.JobsReservationService;
import com.starterkit.springboot.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    @Autowired
    private JobsRepository jobsRepository;
    @Autowired
    private JobsReservationService jobsReservationService;
    String myProductionWeb="https://www.localhost:8090/";

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }


    @GetMapping(value = "/jobs")
    public ModelAndView jobDetails() {
        ModelAndView modelAndView = new ModelAndView("jobs");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
       ArrayList<Jobs> alljobs= jobsReservationService.getAllJobs();
        modelAndView.addObject("jobFormData", new JobsFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("alljobs",alljobs);
        return modelAndView;
    }
    
    @PostMapping(value = "/jobs")
    public ModelAndView addNewJob(@Valid @ModelAttribute("jobsFormData") JobsFormCommand jobsFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("jobs");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        
        modelAndView.addObject("userName", userDto.getFullName());
    
        String randomCode = UUID.randomUUID().toString().replaceAll("-", "");
        String cod= randomCode.substring(0, 6).toUpperCase();
        if (!bindingResult.hasErrors()) {
            try {
                JobsDto jobsDto = new JobsDto()
                        .setJobcode(cod)
                        .setExperience(jobsFormCommand.getExperience())
                        .setJobTitle(jobsFormCommand.getJobTitle())
                        .setDescription(jobsFormCommand.getDescription());
                
             ArrayList<Jobs> jobList =  jobsReservationService.updateJobs(jobsDto);
             modelAndView.addObject("alljobs",jobList);
                modelAndView.addObject("jobsFormData", new JobsFormCommand());
            } catch (Exception ex) {
                bindingResult.rejectValue("jobcode", "error.jobsFormCommand", ex.getMessage());
            }
        }
        return modelAndView;
    }

    
    @GetMapping(value = "/share")
    public ModelAndView shareDetails() {
        ModelAndView modelAndView = new ModelAndView("share");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        String referralLink=myProductionWeb+userDto.getUsername();
        modelAndView.addObject("referralLink",referralLink);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }
   
    @GetMapping(value = "/wallet")
    public ModelAndView getWallet() {
        ModelAndView modelAndView = new ModelAndView("wallet");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }
   
    



  
   
    @GetMapping(value = "/profile")
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
       int profileCompletionpx=userService.getProfileCompletion(userDto);
        ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setMobileNumber(userDto.getMobileNumber());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("profileForm", profileFormCommand);
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
//        int profileCompletionpx=60;
		modelAndView.addObject("getProfileCompletion",profileCompletionpx);
        return modelAndView;
    }

    @PostMapping(value = "/profile")
    public ModelAndView updateProfile(@Valid @ModelAttribute("profileForm") ProfileFormCommand profileFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            userDto.setFirstName(profileFormCommand.getFirstName())
                    .setLastName(profileFormCommand.getLastName())
                    .setMobileNumber(profileFormCommand.getMobileNumber());
            userService.updateProfile(userDto);
            modelAndView.addObject("userName", userDto.getFullName());
        }
        return modelAndView;
    }

    @PostMapping(value = "/password")
    public ModelAndView changePassword(@Valid @ModelAttribute("passwordForm") PasswordFormCommand passwordFormCommand, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("profile");
            ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            modelAndView.addObject("profileForm", profileFormCommand);
            modelAndView.addObject("userName", userDto.getFullName());
            return modelAndView;
        } else {
            userService.changePassword(userDto, passwordFormCommand.getPassword());
            return new ModelAndView("login");
        }
    }

}
