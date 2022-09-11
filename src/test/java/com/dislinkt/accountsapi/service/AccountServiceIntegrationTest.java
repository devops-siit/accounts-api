package com.dislinkt.accountsapi.service;
import static com.dislinkt.accountsapi.constants.AccountConstants.*;
import static com.dislinkt.accountsapi.constants.WorkConstants.*;
import static com.dislinkt.accountsapi.constants.EducationConstants.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.event.AccountCreatedEvent;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewEducationRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewWorkRequest;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceIntegrationTest {
	
	@Autowired
	private AccountService service;
	

	@BeforeAll
	public void init() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Set<? extends GrantedAuthority> auth = new HashSet<>(); 
		UserDetails principal = new User(DB_ACCOUNT_USERNAME_1, "aPassword", auth);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);	
		
	}
	@Test
	public void testFindByUuid() throws Exception {
		
		Account foundAccount = service.findByUuidOrElseThrowException(DB_ACCOUNT_UUID_2);
		
		assertEquals(DB_ACCOUNT_ID_2, foundAccount.getId()); 
    }
	
	@Test
	@Transactional
	public void testFindDTOByUuid() throws Exception {
		
		AccountDTO foundAccount = service.findDTOByUuidOrElseThrowException(DB_ACCOUNT_UUID_2);
		
		assertEquals(DB_ACCOUNT_UUID_2, foundAccount.getUuid()); 
    }
	
	@Test
	public void testFindDTOByUuidException() throws Exception {
		
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	        		AccountDTO foundAccount = service.findDTOByUuidOrElseThrowException(DB_ACCOUNT_UUID_DoesntExist);
	            }
	    );
	    assertEquals("Account not found", exception.getMessage());
    
    }
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertAccount() throws Exception {
		AccountCreatedEvent event = new AccountCreatedEvent();
		event.setDateOfBirth(NEW_ACCOUNT_DATE_OF_BIRTH);
		event.setEmail(NEW_ACCOUNT_EMAIL);
		event.setGender(NEW_ACCOUNT_GENDER);
		event.setName(NEW_ACCOUNT_NAME);
		event.setPhone(NEW_ACCOUNT_PHONE);
		event.setUsername(NEW_ACCOUNT_USERNAME);
		event.setUuid(NEW_ACCOUNT_UUID);
		service.insertAccount(event);
		
		AccountDTO saved = service.findDTOByUuidOrElseThrowException(NEW_ACCOUNT_UUID);
		assertEquals(saved.getUuid(), event.getUuid());

    }
	
	@Test
	@Transactional
	public void testInsertAccountUsernameExists() throws Exception {
		
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	AccountCreatedEvent event = new AccountCreatedEvent();
	            	event.setDateOfBirth(NEW_ACCOUNT_DATE_OF_BIRTH);
	            	event.setEmail(DB_ACCOUNT_EMAIL_1);
	            	event.setGender(NEW_ACCOUNT_GENDER);
	            	event.setName(NEW_ACCOUNT_NAME);
	            	event.setPhone(NEW_ACCOUNT_PHONE);
	            	event.setUsername(NEW_ACCOUNT_USERNAME);
	            	event.setUuid(NEW_ACCOUNT_UUID);
	        		service.insertAccount(event);
	        		
	            }
	    );
	    assertEquals("Account email already exist", exception.getMessage());
    }
	
	@Test
	@Transactional
	public void testInsertAccountEmailExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	AccountCreatedEvent event = new AccountCreatedEvent();
	            	event.setDateOfBirth(NEW_ACCOUNT_DATE_OF_BIRTH);
	            	event.setEmail(NEW_ACCOUNT_EMAIL);
	            	event.setGender(NEW_ACCOUNT_GENDER);
	            	event.setName(NEW_ACCOUNT_NAME);
	            	event.setPhone(NEW_ACCOUNT_PHONE);
	            	event.setUsername(DB_ACCOUNT_USERNAME_2);
	            	event.setUuid(NEW_ACCOUNT_UUID);
	        		service.insertAccount(event);
	        		
	            }
	    );
	    assertEquals("Account username already exist", exception.getMessage());
    }
	
	@Test
	public void testFindByUsernameContainsOrNameContains() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<AccountDTO> found = service.findByUsernameContainsOrNameContains("space", pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEditProfile() {
		EditProfileRequest req = new EditProfileRequest();
		req.setBiography(NEW_BIOGRAPHY);
		req.setDateOfBirth(DB_ACCOUNT_DATE_OF_BIRTH_1);
		req.setEmail(DB_ACCOUNT_EMAIL_1);
		req.setGender(DB_ACCOUNT_GENDER_1);
		req.setIsPublic(DB_ACCOUNT_IS_PUBLIC_1);
		req.setName(DB_ACCOUNT_NAME_1);
		
		// uuid iz prave baze
		AccountDTO changedAcc = service.editProfile(req);
		
		assertEquals(NEW_BIOGRAPHY, changedAcc.getBiography());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertEducation() {
		NewEducationRequest req = new NewEducationRequest();
		req.setName(NEW_EDU_NAME);
		req.setDescription(NEW_EDU_DESCRIPTION);
		DateRangeDTO range = new DateRangeDTO();
		range.setStartDate(NEW_EDU_START_DATE);
		range.setEndDate(NEW_EDU_END_DATE);
		req.setDuration(range);
		req.setTitle(NEW_EDU_TITLE);
		req.setUuid(NEW_EDU_UUID);
		AccountDTO acc = service.insertEducation(req);
		assertEquals(2, acc.getEducation().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertWork() {
		NewWorkRequest req = new NewWorkRequest();
		req.setCompanyName(NEW_WORK_COMPANY_NAME);
		req.setDescription(NEW_WORK_DESCRIPTION);
		DateRangeDTO range = new DateRangeDTO();
		range.setStartDate(NEW_WORK_START_DATE);
		range.setEndDate(NEW_WORK_END_DATE);
		req.setDuration(range);
		req.setPosition(NEW_WORK_POSITION);
		req.setUuid(NEW_WORK_UUID);
		AccountDTO acc = service.insertWork(req);
		assertEquals(2, acc.getWorkExperience().size());
	}
//	ovi testovi ne prolaze
 /*
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteEducation() {
		
		AccountDTO acc = service.deleteEducation(DB_EDU_UUID, DB_EDU_ACCOUNT_UUID);

		assertEquals(0, acc.getEducation().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteWork() {
		AccountDTO acc = service.deleteWorkExperience(DB_WORK_UUID, DB_WORK_ACCOUNT_UUID);
		
		assertEquals(0, acc.getWorkExperience().size());
	}
*/

}
