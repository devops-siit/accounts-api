package com.dislinkt.accountsapi.web.rest.account;

import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewAccountRequest;
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

    @PostMapping
    public ResponseEntity<AccountDTO> insertAccount(@RequestBody NewAccountRequest request) {

        return ReturnResponse.entityCreated(accountService.insertAccount(request));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountDTO>> findByUsernameContainsOrNameContains(@RequestParam String pattern,
                                                                              Pageable pageable) {
        return ReturnResponse.entityGet(accountService.findByUsernameContainsOrNameContains(pattern, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AccountDTO> findByUuid(@PathVariable String uuid) {
        return ReturnResponse.entityGet(accountService.findDTOByUuidOrElseThrowException(uuid));
    }

    @PutMapping
    public ResponseEntity<AccountDTO> editProfile(@RequestBody EditProfileRequest request,
                                               @RequestParam String accountUuid) {

        return ReturnResponse.entityUpdated(accountService.editProfile(accountUuid, request));
    }
}
