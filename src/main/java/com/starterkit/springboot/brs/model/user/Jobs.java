package com.starterkit.springboot.brs.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

	
	
	 
}
