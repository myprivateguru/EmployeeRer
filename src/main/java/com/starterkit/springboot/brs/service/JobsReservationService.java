package com.starterkit.springboot.brs.service;

import com.starterkit.springboot.brs.dto.model.bus.*;
import com.starterkit.springboot.brs.dto.model.user.UserDto;


import java.util.List;
import java.util.Set;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface JobsReservationService {



    //Agency related methods
    AgencyDto getAgency(UserDto userDto);

    AgencyDto addAgency(AgencyDto agencyDto);

    AgencyDto updateAgency(AgencyDto agencyDto, JobsDto jobsDto);

  


}
