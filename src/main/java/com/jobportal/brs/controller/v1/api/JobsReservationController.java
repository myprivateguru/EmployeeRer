package com.jobportal.brs.controller.v1.api;



import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.brs.service.JobsReservationService;
import com.jobportal.brs.service.UserService;


/**
 * Created by Dnyaneshwar Somwanshi.
 */
@RestController
@RequestMapping("/api/v1/reservation")
@Api(value = "brs-application", description = "Operations pertaining to agency management and ticket issue in the BRS application")
public class JobsReservationController {
    @Autowired
    private JobsReservationService busReservationService;

    @Autowired
    private UserService userService;

   
   

    
    
}
