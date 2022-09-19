package com.dislinkt.accountsapi.web.rest.follow;

import com.dislinkt.accountsapi.service.follow.FollowService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
public class FollowResource {

    @Autowired
    private FollowService followService;

    @GetMapping("/followers/{accountUuid}")
    public ResponseEntity<Page<SimpleAccountDTO>> findByTargetAccount(@PathVariable String accountUuid,
                                                                      Pageable pageable) {
        return ReturnResponse.entityGet(followService.findByTargetAccount(accountUuid, pageable));
    }

    @GetMapping("/following/{accountUuid}")
    public ResponseEntity<Page<SimpleAccountDTO>> findBySourceAccount(@PathVariable String accountUuid,
                                                                      Pageable pageable) {
        return ReturnResponse.entityGet(followService.findBySourceAccount(accountUuid, pageable));
    }

    @PatchMapping("/follow/{accountUuid}")
    public ResponseEntity follow(@PathVariable String accountUuid) {
        followService.follow(accountUuid);
        return ReturnResponse.entityUpdated();
    }

    @PatchMapping("/unfollow/{accountUuid}")
    public ResponseEntity unfollow(@PathVariable String accountUuid) {
        followService.unfollow(accountUuid);
        return ReturnResponse.entityUpdated();
    }
}
