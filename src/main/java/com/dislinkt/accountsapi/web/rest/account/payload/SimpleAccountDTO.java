package com.dislinkt.accountsapi.web.rest.account.payload;

import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class SimpleAccountDTO extends BaseDTO {

    private String username;

    private String name;
}
