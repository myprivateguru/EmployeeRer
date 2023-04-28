package com.jobportal.brs.security.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jobportal.brs.dto.model.user.DailyStreakDto;
import com.jobportal.brs.service.DailyStreakService;
import com.jobportal.brs.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserService userService;
	
	@Autowired
	private DailyStreakService dailyStreakService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ADMIN".equals(auth.getAuthority())) {
            	userService.SetLoginHistory();
            	//DailyStreakDto streak = dailyStreakService.getDailyStreak();
                response.sendRedirect("/dashboard");
            }
        }
    }
}
