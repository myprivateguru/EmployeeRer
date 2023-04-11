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
 * Created by Arpit Khandelwal.
 */
@Component
public class BusReservationServiceImpl implements BusReservationService {
    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private BusRepository busRepository;






    

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
     * @param busDto
     * @return
     */
    @Transactional
    public AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto) {
        Agency agency = getAgency(agencyDto.getCode());
        if (agency != null) {
            if (busDto != null) {
                Optional<Bus> bus = Optional.ofNullable(busRepository.findByCodeAndAgency(busDto.getCode(), agency));
                if (!bus.isPresent()) {
                    Bus busModel = new Bus()
                            .setAgency(agency)
                            .setCode(busDto.getCode())
                            .setExperience(busDto.getExperience())
                            .setJobTitle(busDto.getJobTitle())
                            .setDescription(busDto.getDescription());
                    busRepository.save(busModel);
                    if (agency.getBuses() == null) {
                        agency.setBuses(new HashSet<>());
                    }
                    agency.getBuses().add(busModel);
                    return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
                }
                throw exceptionWithId(BUS, DUPLICATE_ENTITY, 2, busDto.getCode(), agencyDto.getCode());
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
     * Returns trip details basd on trip_id
     *
     * @param tripID
     * @return
     */
  

    /**
     * Creates two new Trips with the given information in tripDto object
     *
     * @param tripDto
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
    private Bus getBus(String busCode) {
        return busRepository.findByCode(busCode);
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
