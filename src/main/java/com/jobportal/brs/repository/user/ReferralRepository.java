package com.jobportal.brs.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.brs.model.user.Referral;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrerId(long referrerId);


}