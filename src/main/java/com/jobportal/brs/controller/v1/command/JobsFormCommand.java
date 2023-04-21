package com.jobportal.brs.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Dnyaneshwar Somwanshi.
 */
@Data
@Accessors(chain = true)
public class JobsFormCommand {
    @NotBlank
    @Size(min = 4, max = 8)
    private String jobcode;

    @Min(value = 10, message = "Cannot enroll a bus with capacity smaller than 10")
    private int experience;

    @NotBlank
    private String description;
    
    @NotBlank
    private String jobTitle;
}
