package com.dislinkt.accountsapi.web.rest.follow;

import com.dislinkt.accountsapi.domain.follow.Follow;
import com.dislinkt.accountsapi.service.follow.FollowService;
import com.dislinkt.accountsapi.util.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowResource {

    @Autowired
    private FollowService followService;

    @GetMapping("/followers/{accountUuid}")
    public ResponseEntity<Page<Follow>> findByTargetAccount(@PathVariable String accountUuid,
                                                            Pageable pageable) {
        return ReturnResponse.entityGet(followService.findByTargetAccount(accountUuid, pageable));
    }

    @GetMapping("/following/{accountUuid}")
    public ResponseEntity<Page<Follow>> findBySourceAccount(@PathVariable String accountUuid,
                                                            Pageable pageable) {
        return ReturnResponse.entityGet(followService.findBySourceAccount(accountUuid, pageable));
    }
}
