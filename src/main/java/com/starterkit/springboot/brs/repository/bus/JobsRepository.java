package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Jobs;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface JobsRepository extends CrudRepository<Jobs, Long> {
    Jobs findByJobcode(String busCode);

}
