package com.dislinkt.accountsapi.service;

import static com.dislinkt.accountsapi.constants.AccountConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.service.followrequest.FollowRequestService;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class FollowRequestServiceIntegrationTest {

	@Autowired
	private FollowRequestService service;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testApproveFollowRequest() {
		service.approveFollowRequest(DB_ACCOUNT_UUID_2, DB_ACCOUNT_UUID_3);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(DB_ACCOUNT_UUID_3, pageable);
		
		assertEquals(0, found.getNumberOfElements());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertFollowReques() {
		service.insertFollowRequest(DB_ACCOUNT_UUID_3, DB_ACCOUNT_UUID_4);
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(DB_ACCOUNT_UUID_3, pageable);
		
		assertEquals(2, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeclineFollowRequest() {
		
		service.declineFollowRequest(DB_ACCOUNT_UUID_2, DB_ACCOUNT_UUID_3);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(DB_ACCOUNT_UUID_3, pageable);
		
		assertEquals(0, found.getNumberOfElements());
	}
	
	@Test
	public void testFindByTargetAccount() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(DB_ACCOUNT_UUID_3, pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
}
