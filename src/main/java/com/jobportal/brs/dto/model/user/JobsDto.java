package com.jobportal.brs.dto.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobportal.brs.model.user.Jobs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobsDto {

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
    
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<Jobs> buses;

}
