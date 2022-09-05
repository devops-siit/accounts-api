package com.dislinkt.accountsapi.web.rest.account;

import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewEducationRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewWorkRequest;


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
    public ResponseEntity<AccountDTO> editProfile(@RequestBody EditProfileRequest request) {

        return ReturnResponse.entityUpdated(accountService.editProfile(request));
    }
    
    @PostMapping("/education")
    public ResponseEntity<AccountDTO> insertEducation(@RequestBody NewEducationRequest request) {

        return ReturnResponse.entityCreated(accountService.insertEducation(request));
    	
    }
    
    @PostMapping("/work")
    public ResponseEntity<AccountDTO> insertWorkExperience(@RequestBody NewWorkRequest request) {
    	return ReturnResponse.entityCreated(accountService.insertWork(request));
    
    }
    
    @DeleteMapping("/work/{uuid}")
    public ResponseEntity<AccountDTO> deleteWorkExperience(@PathVariable String uuid) {
    	return ReturnResponse.entityCreated(accountService.deleteWorkExperience(uuid));
    
    }
    
    @DeleteMapping("/education/{uuid}")
    public ResponseEntity<AccountDTO> deleteEducation(@PathVariable String uuid) {
    	return ReturnResponse.entityCreated(accountService.deleteEducation(uuid));
    
    }

}
