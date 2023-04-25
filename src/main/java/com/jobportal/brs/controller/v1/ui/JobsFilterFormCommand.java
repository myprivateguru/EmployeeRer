package com.jobportal.brs.controller.v1.ui;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jobportal.brs.controller.v1.command.JobsFormCommand;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JobsFilterFormCommand {
    @NotBlank
    @Size(min = 4, max = 8)
    private String jobcode;

    @Min(value = 10, message = "Cannot enroll a bus with capacity smaller than 10")
    private int experience;

    @NotBlank
    private String description;
    
    @NotBlank
    private String jobTitle;
    @NotBlank
    private String jobLocation;
    @NotBlank
    private String jobType;
    //full-time, part-time, freelance, etc.
    @NotBlank
    private String jobCategory;
    //e.g., IT, finance, marketing, etc.
    @NotBlank
    private String salaryRange;
    @NotBlank
    private String companyName;
    @NotBlank
    private String applicationDeadline;
}
