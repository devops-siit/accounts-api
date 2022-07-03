package com.dislinkt.accountsapi.service.accounts;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Page<Account> findByUsernameContainsOrNameContains(String pattern, Pageable pageable) {
        return accountRepository.findByUsernameContainsOrProfileNameContains(pattern, pattern, pageable);
    }
}

