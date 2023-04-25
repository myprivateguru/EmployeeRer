package com.jobportal.brs.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jobportal.brs.model.user.User;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

	Optional<User> findById(String ref);

	User findByUsername(String username);

	

}
