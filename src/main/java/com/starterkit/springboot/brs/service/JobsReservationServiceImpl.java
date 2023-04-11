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
    private AgencyRepository agencyRepository;

    @Autowired
    private JobsRepository jobsRepository;






    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

  

    /**
     * Fetch AgencyDto from userDto
     *
     * @param userDto
     * @return
     */
    @Override
    public AgencyDto getAgency(UserDto userDto) {
        User user = getUser(userDto.getEmail());
        if (user != null) {
            Optional<Agency> agency = Optional.ofNullable(agencyRepository.findByOwner(user));
            if (agency.isPresent()) {
                return modelMapper.map(agency.get(), AgencyDto.class);
            }
            throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND, 2, user.getEmail());
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    /**
     * Register a new agency from the Admin signup flow
     *
     * @param agencyDto
     * @return
     */
    @Override
    public AgencyDto addAgency(AgencyDto agencyDto) {
        User admin = getUser(agencyDto.getOwner().getEmail());
        if (admin != null) {
            Optional<Agency> agency = Optional.ofNullable(agencyRepository.findByName(agencyDto.getName()));
            if (!agency.isPresent()) {
                Agency agencyModel = new Agency()
                        .setName(agencyDto.getName())
                        .setRef(agencyDto.getRef())
                        .setCode(RandomStringUtil.getAlphaNumericString(8, agencyDto.getName()))
                        .setOwner(admin);
                agencyRepository.save(agencyModel);
                return modelMapper.map(agencyModel, AgencyDto.class);
            }
            throw exception(AGENCY, DUPLICATE_ENTITY, agencyDto.getName());
        }
        throw exception(USER, ENTITY_NOT_FOUND, agencyDto.getOwner().getEmail());
    }

    /**
     * Updates the agency with given Bus information
     *
     * @param agencyDto
     * @param jobsDto
     * @return
     */
    @Transactional
    public AgencyDto updateAgency(AgencyDto agencyDto, JobsDto jobsDto) {
        Agency agency = getAgency(agencyDto.getCode());
        if (agency != null) {
            if (jobsDto != null) {
                Optional<Jobs> bus = Optional.ofNullable(jobsRepository.findByCodeAndAgency(jobsDto.getCode(), agency));
                if (!bus.isPresent()) {
                    Jobs busModel = new Jobs()
                            .setAgency(agency)
                            .setCode(jobsDto.getCode())
                            .setExperience(jobsDto.getExperience())
                            .setJobTitle(jobsDto.getJobTitle())
                            .setDescription(jobsDto.getDescription());
                    jobsRepository.save(busModel);
                    if (agency.getJobss() == null) {
                        agency.setJobss(new HashSet<>());
                    }
                    agency.getJobss().add(busModel);
                    return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
                }
                throw exceptionWithId(BUS, DUPLICATE_ENTITY, 2, jobsDto.getCode(), agencyDto.getCode());
            } else {
                //update agency details case
                agency.setName(agencyDto.getName())
                        .setRef(agencyDto.getRef());
                return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
            }
        }
        throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND, 2, agencyDto.getOwner().getEmail());
    }




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
        return jobsRepository.findByCode(busCode);
    }

    /**
     * Fetch Agency from agencyCode
     *
     * @param agencyCode
     * @return
     */
    private Agency getAgency(String agencyCode) {
        return agencyRepository.findByCode(agencyCode);
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
