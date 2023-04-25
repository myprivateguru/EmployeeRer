package com.jobportal.brs.service;



import com.jobportal.brs.dto.model.user.JobsDto;
import com.jobportal.brs.exception.BRSException;
import com.jobportal.brs.exception.EntityType;
import com.jobportal.brs.exception.ExceptionType;
import com.jobportal.brs.model.user.Jobs;
import com.jobportal.brs.model.user.User;
import com.jobportal.brs.repository.user.JobsRepository;
import com.jobportal.brs.repository.user.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

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



	@Override
	public ArrayList<Jobs> updateJobs(JobsDto jobsDto) {
		 Optional<Jobs> job = Optional.ofNullable(jobsRepository.findByJobcode(jobsDto.getJobcode()));
	Date date_added = Date.from(Instant.now());
	
		  if (!job.isPresent()) {
              Jobs jobModel = new Jobs()
                      .setJobcode(jobsDto.getJobcode())
                      .setJobTitle(jobsDto.getJobTitle())
                      .setExperience(jobsDto.getExperience())
                      .setDescription(jobsDto.getDescription())
                      .setJobType(jobsDto.getJobType())
                      .setSalaryRange(jobsDto.getSalaryRange())
                      .setJobLocation(jobsDto.getJobLocation())
                      
                      .setJobCategory(jobsDto.getJobCategory())
                      .setCompanyName(jobsDto.getCompanyName())
                      .setApplicationDeadline(jobsDto.getApplicationDeadline())
                      .setDateAdded(date_added)
                      ;
              
              
              jobsRepository.save(jobModel);
		
		
	}
		return (ArrayList<Jobs>) jobsRepository.findAll();
	}



	@Override
	public ArrayList<Jobs> getAllJobs() {
		// TODO Auto-generated method stub
		return (ArrayList<Jobs>) jobsRepository.findAll();
	}






	
	

}
