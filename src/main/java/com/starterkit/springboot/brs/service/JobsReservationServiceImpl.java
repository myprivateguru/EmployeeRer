package com.starterkit.springboot.brs.service;



import com.starterkit.springboot.brs.dto.model.bus.*;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.exception.BRSException;
import com.starterkit.springboot.brs.exception.EntityType;
import com.starterkit.springboot.brs.exception.ExceptionType;
import com.starterkit.springboot.brs.model.bus.*;
import com.starterkit.springboot.brs.model.user.User;
import com.starterkit.springboot.brs.repository.bus.*;
import com.starterkit.springboot.brs.repository.user.UserRepository;
import com.starterkit.springboot.brs.util.RandomStringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.starterkit.springboot.brs.exception.EntityType.*;
import static com.starterkit.springboot.brs.exception.ExceptionType.*;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Component
public class JobsReservationServiceImpl implements JobsReservationService {

    @Autowired
    private JobsRepository jobsRepository;






    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

  


    /**
     * Updates the agency with given Bus information
     *
     * @param agencyDto
     * @param jobsDto
     * @return
     */



    /**
     * Fetch user from UserDto
     *
     * @param email
     * @return
     */
    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

   

    /**
     * Fetch Bus from busCode, since it is unique we don't have issues of finding duplicate Buses
     *
     * @param busCode
     * @return
     */
    private Jobs getBus(String busCode) {
        return jobsRepository.findByJobcode(busCode);
    }

    /**
     * Fetch Agency from agencyCode
     *
     * @param agencyCode
     * @return
     */

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

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BRSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }



	
	

}
