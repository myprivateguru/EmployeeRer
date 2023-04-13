package com.starterkit.springboot.brs.dto.model.bus;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.starterkit.springboot.brs.model.bus.Jobs;

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
    
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<Jobs> buses;

}
