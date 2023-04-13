package com.starterkit.springboot.brs.repository.user;

import com.starterkit.springboot.brs.model.user.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

	Optional<User> findById(String ref);

	User findByUsername(String ref);
	

}
