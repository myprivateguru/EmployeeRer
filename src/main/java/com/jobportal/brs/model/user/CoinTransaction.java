package com.jobportal.brs.model.user;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "coin_transaction")
public class CoinTransaction {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

@Enumerated(EnumType.STRING)
@Column(name = "transaction_type", nullable = false)
private TransactionType transactionType = TransactionType.REFERRAL;

@Column(name = "amount", nullable = false)
private Integer amount;

@Column(name = "source")
private String source;

@Column(name = "created_date", nullable = false)
@Temporal(TemporalType.TIMESTAMP)
private Date createdDate = new Date();

public CoinTransaction(User user, TransactionType transactionType, Integer amount) {
    this.user = user;
    this.transactionType = transactionType;
    this.amount = amount;
}
}

