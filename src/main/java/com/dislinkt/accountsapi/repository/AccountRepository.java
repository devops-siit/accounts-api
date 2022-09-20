package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByUsernameContainsOrProfileNameContains(String username, String name, Pageable pageable);

    Optional<Account> findByUuid(String uuid);

    Optional<Account> findOneByUsername(String username);

    Optional<Account> findOneByProfileEmail(String email);

    @Query(value ="SELECT * FROM account WHERE (username LIKE %?1% OR name LIKE %?2%) AND (is_public = TRUE)", nativeQuery = true)
    Page<Account> findByUsernameContainsOrProfileNameContainsAndProfileIsPublicEquals(String username, String name, boolean isPublic, Pageable pageable);
}