package com.jobportal.brs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jobportal.brs.dto.model.user.DailyStreakDto;
import com.jobportal.brs.model.user.LoginHistory;
import com.jobportal.brs.model.user.User;
import com.jobportal.brs.repository.user.UserRepository;

@Service
public class DailyStreakServiceImpl implements DailyStreakService{
	
	 @Autowired
	    private UserRepository userRepository;

	 @Override
	 public DailyStreakDto getDailyStreak() {
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String currentUserEmail = authentication.getName();
	     User currentUser = userRepository.findByEmail(currentUserEmail);
	     List<LoginHistory> loginHistoryList = currentUser.getLoginHistoryList();
	     Set<LocalDate> distinctDates = loginHistoryList.stream().map(LoginHistory::getLoginTime).map(LocalDateTime::toLocalDate).collect(Collectors.toSet());
	     List<LocalDate> consequentDays = new ArrayList<>();
	     LocalDate today = LocalDate.now();
	     LocalDate yesterday = today.minusDays(1);
	     int streak = 0;
	     boolean yesterdayLogin = distinctDates.contains(yesterday);
	     if (distinctDates.contains(today)) {
	         streak = 1;
	         for (int i = 0; i <= distinctDates.size(); i++) {
	             if (distinctDates.contains(today.minusDays(i))) {
	                 streak++;
	                 consequentDays.add(today.minusDays(i));
	             } else {
	                 break;
	             }
	         }
	     }

	    
	     Collections.sort(consequentDays);
	     int streakNumber = yesterdayLogin ? streak : 0;

	     return new DailyStreakDto().setConsequentDays(consequentDays).setStreakNumber(streakNumber);
	 }



	
}
