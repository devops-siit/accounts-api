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
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        Account loggedAccount = accountService.findByUuidOrElseThrowException(loggedUuid);

        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(loggedAccount.getId(),
                        requestAccount.getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());

        followService.insertFollow(requestAccount, loggedAccount);
    }

    public void insertFollowRequest(String requestAccountUuid, String loggedUuid) {
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        Account loggedAccount = accountService.findByUuidOrElseThrowException(loggedUuid);

        Optional<FollowRequest> followRequestOrEmpty =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(requestAccount.getId(),
                        loggedAccount.getId());

        if (followRequestOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Follow request already added");
        }

        FollowRequest followRequest = new FollowRequest();

        followRequest.setSourceAccount(loggedAccount);
        followRequest.setTargetAccount(requestAccount);

        followRequestRepository.save(followRequest);
    }

    public void declineFollowRequest(String requestAccountUuid, String loggedUuid) {
        Account requestAccount = accountService.findByUuidOrElseThrowException(requestAccountUuid);

        Account loggedAccount = accountService.findByUuidOrElseThrowException(loggedUuid);

        Optional<FollowRequest> followRequest =
                followRequestRepository.findOneByTargetAccountIdAndSourceAccountId(requestAccount.getId(),
                        loggedAccount.getId());

        if (followRequest.isEmpty()) {
            throw new EntityNotFoundException("Follow request not found");
        }

        followRequestRepository.delete(followRequest.get());
    }

    public Page<SimpleAccountDTO> findByTargetAccount(String loggedUid, Pageable pageable) {
        Account loggedAccount = accountService.findByUuidOrElseThrowException(loggedUid);

        Page<FollowRequest> followRequests =
                followRequestRepository.findByTargetAccountId(loggedAccount.getId(), pageable);

        return followRequests.map(followRequest -> {
            SimpleAccountDTO simpleAccountDTO = new SimpleAccountDTO();

            simpleAccountDTO.setUsername(followRequest.getSourceAccount().getUsername());
            simpleAccountDTO.setName(followRequest.getSourceAccount().getProfile().getName());
            simpleAccountDTO.setUuid(followRequest.getSourceAccount().getUuid());

            return simpleAccountDTO;
        });
    }
}
