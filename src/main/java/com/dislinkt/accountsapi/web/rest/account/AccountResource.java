package com.dislinkt.accountsapi.web.rest.account;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @GetMapping("/search")
    public ResponseEntity<Page<Account>> findByUsernameContainsOrNameContains(@RequestParam String pattern,
                                                                              Pageable pageable) {
        return ReturnResponse.entityGet(accountService.findByUsernameContainsOrNameContains(pattern, pageable));
    }

    @GetMapping
    public ResponseEntity<Account> findByUuid(@RequestParam String uuid) {
        Optional<Account> account = accountService.findByUuid(uuid);

        if (account.isEmpty()) {
            return ReturnResponse.entityNotFound();
        }

        return ReturnResponse.entityGet(account.get());
    }

    @PutMapping
    public ResponseEntity<Account> editProfile(@RequestBody EditProfileRequest request,
                                               @RequestParam String accountUuid) {

        return ReturnResponse.entityUpdated(accountService.editProfile(accountUuid, request));
    }
}
