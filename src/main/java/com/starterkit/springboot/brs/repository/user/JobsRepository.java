package com.starterkit.springboot.brs.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.starterkit.springboot.brs.model.user.Jobs;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface JobsRepository extends CrudRepository<Jobs, Long> {
    Jobs findByJobcode(String busCode);

}
