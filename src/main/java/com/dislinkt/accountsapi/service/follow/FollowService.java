package com.dislinkt.accountsapi.service.follow;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.follow.Follow;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.repository.FollowRepository;
import com.dislinkt.accountsapi.util.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void insertFollow(Account targetAccount, Account sourceAccount) {
        Follow follow = new Follow();

        follow.setTargetAccount(targetAccount);
        follow.setSourceAccount(sourceAccount);

        followRepository.save(follow);
    }

    public Page<Follow> findByTargetAccount(@PathVariable String accountUuid,
                                                            Pageable pageable) {

        Optional<Account> account = accountRepository.findByUuid(accountUuid);

        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        return followRepository.findByTargetAccountId(account.get().getId(), pageable);
    }

    public Page<Follow> findBySourceAccount(@PathVariable String accountUuid,
                                            Pageable pageable) {

        Optional<Account> account = accountRepository.findByUuid(accountUuid);

        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        return followRepository.findBySourceAccountId(account.get().getId(), pageable);
    }
}
