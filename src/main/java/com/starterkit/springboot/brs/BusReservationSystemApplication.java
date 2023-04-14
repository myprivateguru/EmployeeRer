package com.starterkit.springboot.brs;


import com.starterkit.springboot.brs.model.user.Role;
import com.starterkit.springboot.brs.model.user.User;
import com.starterkit.springboot.brs.model.user.UserRoles;
import com.starterkit.springboot.brs.repository.user.JobsRepository;
import com.starterkit.springboot.brs.repository.user.RoleRepository;
import com.starterkit.springboot.brs.repository.user.UserRepository;
import com.starterkit.springboot.brs.util.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BusReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusReservationSystemApplication.class, args);
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
