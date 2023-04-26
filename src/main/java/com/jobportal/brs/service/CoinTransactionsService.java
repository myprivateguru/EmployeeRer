package com.jobportal.brs.service;

import java.util.List;

import com.jobportal.brs.model.user.CoinTransaction;
import com.jobportal.brs.model.user.User;


public interface CoinTransactionsService {
	List<CoinTransaction> findByUser(User user);

}
