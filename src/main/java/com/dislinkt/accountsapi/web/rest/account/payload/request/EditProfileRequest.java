package com.dislinkt.accountsapi.web.rest.account.payload.request;

import com.dislinkt.accountsapi.domain.account.Gender;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class EditProfileRequest {

    private String name;

    private String email;

    private Gender gender;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOfBirth;

    private String biography;

    private Boolean isPublic;
}
