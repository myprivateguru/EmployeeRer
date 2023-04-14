package com.starterkit.springboot.brs.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starterkit.springboot.brs.model.user.Referral;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrerId(long referrerId);
}