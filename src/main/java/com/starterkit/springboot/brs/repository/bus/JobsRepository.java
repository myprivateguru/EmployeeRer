package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Agency;
import com.starterkit.springboot.brs.model.bus.Jobs;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
public interface JobsRepository extends CrudRepository<Jobs, Long> {
    Jobs findByCode(String busCode);

    Jobs findByCodeAndAgency(String busCode, Agency agency);
}
