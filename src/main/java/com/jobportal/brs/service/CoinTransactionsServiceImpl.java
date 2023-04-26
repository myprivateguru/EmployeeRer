package com.jobportal.brs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobportal.brs.model.user.CoinTransaction;
import com.jobportal.brs.model.user.User;
import com.jobportal.brs.repository.user.CoinTransactionRepository;
@Component
public class CoinTransactionsServiceImpl implements CoinTransactionsService{
	
	@Autowired
	private CoinTransactionRepository coinTransactionRepository;

	@Override
	public List<CoinTransaction> findByUser(User user) {
		//coinTransactionRepository.findByUser(user);
		return coinTransactionRepository.findByUser(user);
	}
	

}
