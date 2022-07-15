package com.dislinkt.accountsapi.web.rest.account.payload.request;

import com.dislinkt.accountsapi.domain.account.Gender;
import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewAccountRequest extends BaseDTO {

    private String username;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOfBirth;
}
