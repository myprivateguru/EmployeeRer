package com.starterkit.springboot.brs.service;

import com.starterkit.springboot.brs.dto.model.bus.*;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.model.bus.Bus;

import java.util.List;
import java.util.Set;

/**
 * Created by Arpit Khandelwal.
 */
public interface BusReservationService {



    //Agency related methods
    AgencyDto getAgency(UserDto userDto);

    AgencyDto addAgency(AgencyDto agencyDto);

    AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto);

  


}
