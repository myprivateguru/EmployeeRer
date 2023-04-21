package com.jobportal.brs;


import com.jobportal.brs.model.user.Role;
import com.jobportal.brs.model.user.User;
import com.jobportal.brs.model.user.UserRoles;
import com.jobportal.brs.repository.user.JobsRepository;
import com.jobportal.brs.repository.user.RoleRepository;
import com.jobportal.brs.repository.user.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
@SpringBootApplication
public class EmployeeReferralSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeReferralSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
                          
                           JobsRepository busRepository) {
        return args -> {
            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(UserRoles.ADMIN);
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole(UserRoles.PASSENGER);
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("admin@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setUsername("admin")
                        .setMobileNumber("9425094250")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(admin);
            }

           

            

            
        };
    }
}
