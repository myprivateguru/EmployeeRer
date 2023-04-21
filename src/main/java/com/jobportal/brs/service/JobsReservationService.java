package com.jobportal.brs.service;


import com.jobportal.brs.dto.model.user.JobsDto;
import com.jobportal.brs.dto.model.user.UserDto;
import com.jobportal.brs.model.user.Jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface JobsReservationService {

	ArrayList<Jobs> updateJobs(JobsDto jobsDto);

	ArrayList<Jobs> getAllJobs();

	/*
	 * JobPortal updateJobs(JobsDto jobsDto);
	 */

}
