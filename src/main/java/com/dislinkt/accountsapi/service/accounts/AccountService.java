package com.dislinkt.accountsapi.service.accounts;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Page<Account> findByUsernameContainsOrNameContains(String pattern, Pageable pageable) {
        return accountRepository.findByUsernameContainsOrProfileNameContains(pattern, pattern, pageable);
    }

    public Optional<Account> findByUuid(String uuid) {
        return accountRepository.findByUuid(uuid);
    }

    public Account editProfile(String accountUuid, EditProfileRequest request) {
        Optional<Account> accountOrEmpty = accountRepository.findByUuid(accountUuid);

        if (accountOrEmpty.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        Account account = accountOrEmpty.get();

        account.getProfile().setName(request.getName());
        account.getProfile().setEmail(request.getEmail());
        account.getProfile().setGender(request.getGender());
        account.getProfile().setBiography(request.getBiography());
        account.getProfile().setDateOfBirth(request.getDateOfBirth());
        account.getProfile().setIsPublic(request.getIsPublic());

        accountRepository.save(account);

        return account;
    }
}

