package com.jobportal.brs.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

import javax.persistence.*;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "jobs")
public class Jobs {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobcode;

    private int experience;

    private String description;
    
    private String jobTitle;
    
    private String jobLocation;
    
    private String jobType;
    //full-time, part-time, freelance, etc.
    
    private String jobCategory;
    //e.g., IT, finance, marketing, etc.
    
    private String salaryRange;
    
    private String companyName;
    
    private String applicationDeadline;
    
   private Date dateAdded;
    

	
	
	 
}
