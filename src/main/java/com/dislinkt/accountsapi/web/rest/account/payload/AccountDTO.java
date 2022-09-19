package com.dislinkt.accountsapi.web.rest.account.payload;


import java.time.LocalDateTime;
import java.util.Set;

import com.dislinkt.accountsapi.domain.account.Gender;
import com.dislinkt.accountsapi.web.rest.base.BaseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class AccountDTO extends BaseDTO {

    private String username;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOfBirth;

    private String biography;

    private Boolean isPublic = true;

    private Integer followingCount = 0;

    private Integer followersCount = 0;
    
    private Set<EducationDTO> education;
    
    private Set<WorkDTO> workExperience;

}
