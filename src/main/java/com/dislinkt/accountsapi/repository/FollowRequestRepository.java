package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.follow.request.FollowRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {

    Page<FollowRequest> findByTargetAccountId(Long targetAccountId, Pageable pageable);

    Optional<FollowRequest> findOneByTargetAccountIdAndSourceAccountId(Long targetAccountId, Long sourceAccountId);
    
    Page<FollowRequest> findBySourceAccountId(Long sourceAccountId, Pageable pageable);
}
