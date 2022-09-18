package com.dislinkt.accountsapi.service;

import static com.dislinkt.accountsapi.constants.EducationConstants.DB_EDU_ACCOUNT_UUID;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_DESCRIPTION;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_END_DATE;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_NAME;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_START_DATE;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_TITLE;
import static com.dislinkt.accountsapi.constants.EducationConstants.NEW_EDU_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.education.Education;
import com.dislinkt.accountsapi.domain.base.DateRange;
import com.dislinkt.accountsapi.repository.EducationRepository;
import com.dislinkt.accountsapi.service.education.EducationService;
import com.dislinkt.accountsapi.web.rest.account.payload.EducationDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewEducationRequest;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EducationServiceUnitTest {
	
	@Autowired
	@InjectMocks
	private EducationService service;
	
	@Mock
	private EducationRepository repository;
	
	@BeforeAll
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertAccount() {
		
		NewEducationRequest req = new NewEducationRequest();
		req.setName(NEW_EDU_NAME);
		req.setDescription(NEW_EDU_DESCRIPTION);
		DateRangeDTO range = new DateRangeDTO();
		range.setStartDate(NEW_EDU_START_DATE);
		range.setEndDate(NEW_EDU_END_DATE);
		req.setDuration(range);
		req.setTitle(NEW_EDU_TITLE);
		req.setUuid(NEW_EDU_UUID);
		Account acc = new Account();
		acc.setUuid(DB_EDU_ACCOUNT_UUID);
		
		
		DateRange dateRange = new DateRange();
		Education edu = new Education();
		edu.setDescription(req.getDescription());
		edu.setDuration(dateRange);
		edu.setName(req.getName());
		edu.setTitle(req.getTitle());
		edu.setUuid(req.getUuid());
		
		when(repository.save(edu)).thenReturn(edu);
		
		EducationDTO saved = service.insertEducation(req, acc);
		assertEquals(req.getName(), saved.getName());
	}
	

}
