package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.follow.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Page<Follow> findByTargetAccountId(Long targetAccountId, Pageable pageable);

    Page<Follow> findBySourceAccountId(Long sourceAccountId, Pageable pageable);
}