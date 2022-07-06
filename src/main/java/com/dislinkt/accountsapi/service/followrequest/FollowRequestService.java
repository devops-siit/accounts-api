package com.dislinkt.accountsapi.service.followrequest;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.follow.request.FollowRequest;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.FollowRequestRepository;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.service.follow.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public void approveFollowRequest(String requestAccountUuid, String loggedUuid) {
        Optional<Account> requestAccount = accountService.findByUuid(requestAccountUuid);

        if (requestAccount.isEmpty()) {
            throw new EntityNotFoundException("Requested account not found");
        }

        Optional<Account> loggedAccount = accountService.findByUuid(loggedUuid);

        if (loggedAccount.isEmpty()) {
            throw new EntityNotFoundException("Logged account not found");
        }

        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(requestAccount.get().getId(),
                        loggedAccount.get().getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());

        followService.insertFollow(requestAccount.get(), loggedAccount.get());
    }

    public void insertFollowRequest(String requestAccountUuid, String loggedUuid) {
        Optional<Account> requestAccount = accountService.findByUuid(requestAccountUuid);

        if (requestAccount.isEmpty()) {
            throw new EntityNotFoundException("Requested account not found");
        }

        Optional<Account> loggedAccount = accountService.findByUuid(loggedUuid);

        if (loggedAccount.isEmpty()) {
            throw new EntityNotFoundException("Logged account not found");
        }

        FollowRequest followRequest = new FollowRequest();

        followRequest.setSourceAccount(loggedAccount.get());
        followRequest.setTargetAccount(requestAccount.get());

        followRequestRepository.save(followRequest);
    }

    public void declineFollowRequest(String requestAccountUuid, String loggedUuid) {
        Optional<Account> requestAccount = accountService.findByUuid(requestAccountUuid);

        if (requestAccount.isEmpty()) {
            throw new EntityNotFoundException("Requested account not found");
        }

        Optional<Account> loggedAccount = accountService.findByUuid(loggedUuid);

        if (loggedAccount.isEmpty()) {
            throw new EntityNotFoundException("Logged account not found");
        }

        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(requestAccount.get().getId(),
                        loggedAccount.get().getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());
    }

    public Page<FollowRequest> findByTargetAccount(String loggedUid, Pageable pageable) {
        Optional<Account> loggedAccount = accountService.findByUuid(loggedUid);

        if (loggedAccount.isEmpty()) {
            throw new EntityNotFoundException("Logged account not found");
        }

        Page<FollowRequest> followRequests =
                followRequestRepository.findByTargetAccountId(loggedAccount.get().getId(), pageable);

        return followRequests;
    }
}
