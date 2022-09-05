package com.dislinkt.accountsapi.web.rest.followrequest;

import com.dislinkt.accountsapi.service.followrequest.FollowRequestService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow-requests")
public class FollowRequestResource {

    @Autowired
    private FollowRequestService followRequestService;

    @GetMapping
    public ResponseEntity<Page<SimpleAccountDTO>> findByTargetAccount(Pageable pageable) {
        return ReturnResponse.entityGet(followRequestService.findByTargetAccount(pageable));
    }

    @PostMapping("/{requestAccountUuid}")
    public ResponseEntity insertFollowRequest(@PathVariable String requestAccountUuid) {

        followRequestService.insertFollowRequest(requestAccountUuid);
        return ReturnResponse.entityCreated();
    }

    @PostMapping("/approve/{requestAccountUuid}")
    public ResponseEntity approveFollowRequest(@PathVariable String requestAccountUuid) {

        followRequestService.approveFollowRequest(requestAccountUuid);
        return ReturnResponse.entityCreated();
    }

    @PostMapping("/decline/{requestAccountUuid}")
    public ResponseEntity declineFollowRequest(@PathVariable String requestAccountUuid) {

        followRequestService.declineFollowRequest(requestAccountUuid);
        return ReturnResponse.entityCreated();
    }
}
