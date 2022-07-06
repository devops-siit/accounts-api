package com.dislinkt.accountsapi.web.rest.followrequest;

import com.dislinkt.accountsapi.domain.follow.request.FollowRequest;
import com.dislinkt.accountsapi.service.followrequest.FollowRequestService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow-request")
public class FollowRequestResource {

    @Autowired
    private FollowRequestService followRequestService;

    @GetMapping("/{accountUuid}")
    public ResponseEntity<Page<FollowRequest>> findByTargetAccount(@PathVariable String accountUuid,
                                                                   Pageable pageable) {
        return ReturnResponse.entityGet(followRequestService.findByTargetAccount(accountUuid, pageable));
    }

    @PostMapping("/{requestAccountUuid}")
    public ResponseEntity insertFollowRequest(@PathVariable String requestAccountUuid,
                                              @RequestParam String loggedUuid) {

        followRequestService.insertFollowRequest(requestAccountUuid, loggedUuid);
        return ReturnResponse.entityCreated();
    }

    @PostMapping("/approve/{requestAccountUuid}")
    public ResponseEntity approveFollowRequest(@PathVariable String requestAccountUuid,
                                               @RequestParam String loggedUuid) {

        followRequestService.approveFollowRequest(requestAccountUuid, loggedUuid);
        return ReturnResponse.entityCreated();
    }
}
