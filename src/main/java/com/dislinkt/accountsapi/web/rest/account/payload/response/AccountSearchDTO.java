package com.dislinkt.accountsapi.web.rest.account.payload.response;

import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class AccountSearchDTO extends BaseDTO {

    private String username;

    private String name;
}
