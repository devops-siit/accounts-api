package com.dislinkt.accountsapi.repository;

import com.dislinkt.accountsapi.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByUsernameContainsOrProfileNameContains(String username, String name, Pageable pageable);
}