package com.jobportal.brs.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.brs.model.user.CoinTransaction;
import com.jobportal.brs.model.user.User;

public interface CoinTransactionRepository extends CrudRepository<CoinTransaction, Long> {

	List<CoinTransaction> findByUser(User user);

}
