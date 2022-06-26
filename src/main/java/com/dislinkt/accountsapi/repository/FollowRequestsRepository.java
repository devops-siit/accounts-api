package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.follow.request.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRequestsRepository extends JpaRepository<FollowRequest, Long> {

}