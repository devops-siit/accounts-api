package com.dislinkt.accountsapi.web.rest.account;

import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/search")
    public ResponseEntity<Page<AccountDTO>> findByUsernameContainsOrNameContains(@RequestParam String pattern,
                                                                                 Pageable pageable) {
        return ReturnResponse.entityGet(accountService.findByUsernameContainsOrNameContains(pattern, pageable)
                .map(account -> modelMapper.map(account, AccountDTO.class)));
    }
}
