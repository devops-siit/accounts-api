package com.dislinkt.accountsapi.service.follow;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.follow.Follow;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.repository.FollowRepository;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<SimpleAccountDTO> findByTargetAccount(String accountUuid,
                                                      Pageable pageable) {

        Optional<Account> account = accountRepository.findByUuid(accountUuid);

        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        return followRepository.findByTargetAccountId(account.get().getId(), pageable).map(follow -> {
            SimpleAccountDTO dto = new SimpleAccountDTO();
            dto.setUsername(follow.getTargetAccount().getUsername());
            dto.setName(follow.getTargetAccount().getProfile().getName());
            dto.setUuid(follow.getTargetAccount().getUuid());

            return dto;
        });
    }

    public Page<SimpleAccountDTO> findBySourceAccount(String accountUuid,
                                                      Pageable pageable) {

        Optional<Account> account = accountRepository.findByUuid(accountUuid);

        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        return followRepository.findBySourceAccountId(account.get().getId(), pageable).map(follow -> {
            SimpleAccountDTO dto = new SimpleAccountDTO();
            dto.setUsername(follow.getTargetAccount().getUsername());
            dto.setName(follow.getTargetAccount().getProfile().getName());
            dto.setUuid(follow.getTargetAccount().getUuid());

            return dto;
        });
    }
}
