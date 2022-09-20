package com.dislinkt.accountsapi.service.follow;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.follow.Follow;
import com.dislinkt.accountsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.repository.FollowRepository;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AccountService accountService;

    public void insertFollow(Account targetAccount, Account sourceAccount) {
        Follow follow = new Follow();

        follow.setTargetAccount(targetAccount);
        follow.setSourceAccount(sourceAccount);

        followRepository.save(follow);
    }

    public Page<SimpleAccountDTO> findByTargetAccount(String accountUuid,
                                                      Pageable pageable) {

        Account account = accountService.findByUuidOrElseThrowException(accountUuid);

        return followRepository.findByTargetAccountId(account.getId(), pageable).map(follow -> {
            SimpleAccountDTO dto = new SimpleAccountDTO();
            dto.setUsername(follow.getTargetAccount().getUsername());
            dto.setName(follow.getTargetAccount().getProfile().getName());
            dto.setUuid(follow.getTargetAccount().getUuid());

            return dto;
        });
    }

    public Page<SimpleAccountDTO> findBySourceAccount(String accountUuid,
                                                      Pageable pageable) {

        Account account = accountService.findByUuidOrElseThrowException(accountUuid);

        return followRepository.findBySourceAccountId(account.getId(), pageable).map(follow -> {
            SimpleAccountDTO dto = new SimpleAccountDTO();
            dto.setUsername(follow.getTargetAccount().getUsername());
            dto.setName(follow.getTargetAccount().getProfile().getName());
            dto.setUuid(follow.getTargetAccount().getUuid());

            return dto;
        });
    }

    public void follow(String accountUuid) {

        Account account = accountService.findByUuidOrElseThrowException(accountUuid);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account logged = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Follow follow = new Follow();
        follow.setSourceAccount(logged);
        follow.setTargetAccount(account);

        followRepository.save(follow);
    }

    public void unfollow(String accountUuid) {

        Account account = accountService.findByUuidOrElseThrowException(accountUuid);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account logged = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Optional<Follow> follow = followRepository.findBySourceAccountIdAndTargetAccountId(logged.getId(), account.getId());

        if (follow.isEmpty()) {
            throw new EntityNotFoundException("Follow not found");
        }

        followRepository.delete(follow.get());
    }
}
