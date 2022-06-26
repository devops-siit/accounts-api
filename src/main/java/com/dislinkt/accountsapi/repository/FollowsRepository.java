package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsRepository extends JpaRepository<Follow, Long> {

}