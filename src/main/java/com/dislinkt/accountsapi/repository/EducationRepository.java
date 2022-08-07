package com.dislinkt.accountsapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.accountsapi.domain.account.education.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

	Optional<Education> findOneByUuid(String uuid);

}
