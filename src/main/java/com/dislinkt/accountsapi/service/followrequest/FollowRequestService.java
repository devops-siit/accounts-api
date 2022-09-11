package com.dislinkt.accountsapi.service.followrequest;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.follow.request.FollowRequest;
import com.dislinkt.accountsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.FollowRequestRepository;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.service.follow.FollowService;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowRequestService {

    @Autowired
    private FollowRequestRepository followRequestRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FollowService followService;

    public void approveFollowRequest(String requestAccountUuid) {
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(account.getId(),
                        requestAccount.getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());

        followService.insertFollow(requestAccount, account);
    }

    public void insertFollowRequest(String requestAccountUuid) {
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Optional<FollowRequest> followRequestOrEmpty =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(requestAccount.getId(),
                        account.getId());

        if (followRequestOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Follow request already added");
        }

        FollowRequest followRequest = new FollowRequest();

        followRequest.setSourceAccount(requestAccount);
        followRequest.setTargetAccount(account);

        followRequestRepository.save(followRequest);
    }

    public void declineFollowRequest(String requestAccountUuid) {
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());
        // request account = source account
        // logged user = target account
        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(account.getId(),requestAccount.getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());
    }

    public Page<SimpleAccountDTO> findByTargetAccount(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Page<FollowRequest> followRequests =
                followRequestRepository.findByTargetAccountId(account.getId(), pageable);

        return followRequests.map(followRequest -> {
            SimpleAccountDTO simpleAccountDTO = new SimpleAccountDTO();

            simpleAccountDTO.setUsername(followRequest.getSourceAccount().getUsername());
            simpleAccountDTO.setName(followRequest.getSourceAccount().getProfile().getName());
            simpleAccountDTO.setUuid(followRequest.getSourceAccount().getUuid());

            return simpleAccountDTO;
        });
    }
}
