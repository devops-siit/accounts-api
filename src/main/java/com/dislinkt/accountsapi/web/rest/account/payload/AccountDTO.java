package com.dislinkt.accountsapi.web.rest.account.payload;

import com.dislinkt.accountsapi.domain.account.Gender;
import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountDTO extends BaseDTO {

    private String username;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    private LocalDateTime dateOfBirth;

    private String biography;

    private Boolean isPublic = true;

    private Integer followingCount = 0;

    private Integer followersCount = 0;
}
