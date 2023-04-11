package com.starterkit.springboot.brs.controller.v1.api;



import com.starterkit.springboot.brs.service.JobsReservationService;
import com.starterkit.springboot.brs.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
