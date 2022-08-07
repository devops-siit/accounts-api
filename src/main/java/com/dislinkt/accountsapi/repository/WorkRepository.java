package com.dislinkt.accountsapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.accountsapi.domain.account.work.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{

	Optional<Work> findOneByUuid(String uuid);

}
