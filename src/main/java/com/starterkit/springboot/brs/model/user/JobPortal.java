package com.starterkit.springboot.brs.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/*
 * @Setter
 * 
 * @Getter
 * 
 * @Entity
 * 
 * @Table(name = "jobportal", indexes = @Index( name = "idx_agency_code",
 * columnList = "code", unique = true )) public class JobPortal {
 * 
 * @Id
 * 
 * @Column(name = "agency_id")
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * @OneToMany(mappedBy = "jobportal", cascade = CascadeType.ALL) private
 * Set<Jobs> joblist;
 * 
 * }
 */