package com.dislinkt.accountsapi.service;

import static com.dislinkt.accountsapi.constants.AccountConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.Profile;
import com.dislinkt.accountsapi.service.follow.FollowService;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class FollowServiceIntegrationTest {

	@Autowired
	private FollowService service;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertFollow() {
		Account targetAcc = new Account();
		Account sourceAcc = new Account();
		
		targetAcc.setId(DB_ACCOUNT_ID_1);
		targetAcc.setUuid(DB_ACCOUNT_UUID_1);
		
		Profile profile = new Profile();
		profile.setName(DB_ACCOUNT_NAME_1);
		targetAcc.setProfile(profile);
		sourceAcc.setId(DB_ACCOUNT_ID_2);
		sourceAcc.setUuid(DB_ACCOUNT_UUID_2);
		
		service.insertFollow(targetAcc, sourceAcc);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(targetAcc.getUuid(), pageable);
		
		assertEquals(1, found.getNumberOfElements());
		
	}
	
	@Test
	public void testFindByTargetAccount() {
		
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findByTargetAccount(DB_ACCOUNT_UUID_3, pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	public void testFindBySourceAccount() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<SimpleAccountDTO> found = service.findBySourceAccount(DB_ACCOUNT_UUID_1, pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
}
