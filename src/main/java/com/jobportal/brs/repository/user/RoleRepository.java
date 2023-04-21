package com.jobportal.brs.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.brs.model.user.Role;
import com.jobportal.brs.model.user.UserRoles;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(UserRoles role);

}
