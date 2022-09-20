package com.dislinkt.accountsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.dislinkt.accountsapi.constants.AccountConstants.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.service.followrequest.FollowRequestService;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FollowRequestServiceIntegrationTest {

	@Autowired
	private FollowRequestService service;
	
	@BeforeAll
	public void init() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Set<? extends GrantedAuthority> auth = new HashSet<>(); 
		UserDetails principal = new User(DB_ACCOUNT_USERNAME_3, "aPassword", auth);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);	
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testApproveFollowRequest() {
		service.approveFollowRequest(DB_ACCOUNT_UUID_2);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount( pageable);
		
		assertEquals(0, found.getNumberOfElements());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertFollowReques() {
		service.insertFollowRequest(DB_ACCOUNT_UUID_4);
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeclineFollowRequest() {
		
		service.declineFollowRequest(DB_ACCOUNT_UUID_2);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount( pageable);
		
		assertEquals(0, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	public void testFindByTargetAccount() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
}
