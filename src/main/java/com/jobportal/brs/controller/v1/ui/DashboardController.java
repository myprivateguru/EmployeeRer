package com.jobportal.brs.controller.v1.ui;

import com.jobportal.brs.controller.v1.command.*;
import com.jobportal.brs.dto.model.user.JobsDto;
import com.jobportal.brs.dto.model.user.ProfileCompletionDto;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.model.user.Jobs;
import com.jobportal.brs.repository.user.JobsRepository;
import com.jobportal.brs.service.JobsReservationService;
import com.jobportal.brs.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Controller
public class DashboardController {
	Long jobId;
    @Autowired
    private UserService userService;
    @Autowired
    private JobsRepository jobsRepository;
    @Autowired
    private JobsReservationService jobsReservationService;
    
    @Value("${spring.datasource.url}")
    String myProductionWeb;

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
    public ModelAndView jobListed(@Valid @ModelAttribute("filterFormData") JobsFormCommand filterFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("jobs");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        final Logger logger = LogManager.getLogger(DashboardController.class);
        logger.info("getting info log!");
       ArrayList<Jobs> alljobs= jobsReservationService.getAllJobs();
       List<Jobs> filteredJobs = alljobs.stream()
   		    .filter(job -> job.getExperience()>3)
   		   // .filter(job -> job.getJobCategory().equalsIgnoreCase("software"))
   		    .collect(Collectors.toList());
      
        modelAndView.addObject("filterFormData", new JobsFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("alljobs",filteredJobs);
        return modelAndView;
    }
    
    public List<String> getNonNullFields(JobsFormCommand jobsFormCommand) {
        List<String> fields = new ArrayList<>();

        Field[] declaredFields = JobsFormCommand.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(jobsFormCommand);
                if (value != null && !value.toString().isEmpty()) {
                    fields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // handle exception
            }
        }

        return fields;
    }


    @PostMapping(value = "/jobs")
    public ModelAndView jobsFiltered(@Valid @ModelAttribute("jobsFormData") JobsFormCommand jobsFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("jobs");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        final Logger logger = LogManager.getLogger(DashboardController.class);
        logger.info("getting info log!");

        ArrayList<Jobs> alljobs= jobsReservationService.getAllJobs();
     // Get all fields of JobsFormCommand class
        Field[] fields = JobsFormCommand.class.getDeclaredFields();

        // Create a list to hold the names of non-null fields
        List<String> nonNullFields = new ArrayList<>();

        // Loop through all fields and check if they are non-null in the JobsFormCommand instance
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(jobsFormCommand);
                if (value != null && !value.toString().isEmpty()) {
                    nonNullFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // Handle exception
            }
        }

        // Now use the nonNullFields list to filter jobs
        List<Jobs> filteredJobs = alljobs.stream()
                .filter(job -> {
                    for (String field : nonNullFields) {
                        try {
                        	// write logic here to filter jobs based on customized inputs
                        } catch (IllegalArgumentException | SecurityException  e) {
                            // Handle exception
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());


        modelAndView.addObject("jobFormData", new JobsFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("alljobs", filteredJobs);
        return modelAndView;
    }




    
    @GetMapping(value = "/addjobs")
    public ModelAndView addNewJob() {
        ModelAndView modelAndView = new ModelAndView("addjobs");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        final Logger logger = LogManager.getLogger(DashboardController.class);
        logger.info("getting info log!");
        modelAndView.addObject("jobFormData", new JobsFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }
    
    @PostMapping(value = "/addjobs")
    public ModelAndView addNewJob(@Valid @ModelAttribute("jobsFormData") JobsFormCommand jobsFormCommand, BindingResult bindingResult) {
    	
    	ModelAndView modelAndView = new ModelAndView("addjobs");
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
                        .setDescription(jobsFormCommand.getDescription())
                        .setJobLocation(jobsFormCommand.getJobLocation())
                        .setJobCategory(jobsFormCommand.getJobCategory())
                        .setJobType(jobsFormCommand.getJobType())
                        .setCompanyName(jobsFormCommand.getCompanyName())
                        .setSalaryRange(jobsFormCommand.getSalaryRange())
                        .setApplicationDeadline(jobsFormCommand.getApplicationDeadline())
                        ;
                
                
             ArrayList<Jobs> jobList =  jobsReservationService.updateJobs(jobsDto);
            Jobs job= jobsRepository.findByJobcode(cod);
             jobId = job.getId();
            this.jobDetails(jobId);
             
            } catch (Exception ex) {
                bindingResult.rejectValue("jobcode", "error.jobsFormCommand", ex.getMessage());
            }
        }
        return this.jobDetails(jobId);
    }
    @GetMapping(value = "/jobDetails/{jobId}")
    public ModelAndView jobDetails(@PathVariable Long jobId) {
        ModelAndView modelAndView = new ModelAndView("jobDetails");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        final Logger logger = LogManager.getLogger(DashboardController.class);
        logger.info("getting info log!");
       Optional<Jobs> job = jobsRepository.findById(jobId);
       ArrayList<Jobs> alljobs= jobsReservationService.getAllJobs();
        modelAndView.addObject("jobFormData", new JobsFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("job", job.orElse(null));
        return modelAndView;
    }
    
    @GetMapping(value = "/share")
    public ModelAndView shareDetails() {
        ModelAndView modelAndView = new ModelAndView("share");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        String referralLink=myProductionWeb+"signup?ref="+userDto.getUsername();
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
        ProfileCompletionDto profileCompletionpx = userService.getProfileCompletion(userDto);
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
        ProfileCompletionDto profileCompletionpx = userService.getProfileCompletion(userDto);
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
            modelAndView.addObject("getProfileCompletion",profileCompletionpx);
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
