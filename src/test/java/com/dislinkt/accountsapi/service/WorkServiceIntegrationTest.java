package com.dislinkt.accountsapi.service;

import static com.dislinkt.accountsapi.constants.WorkConstants.DB_WORK_ACCOUNT_ID;
import static com.dislinkt.accountsapi.constants.WorkConstants.DB_WORK_ACCOUNT_UUID;
import static com.dislinkt.accountsapi.constants.WorkConstants.DB_WORK_UUID;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_COMPANY_NAME;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_DESCRIPTION;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_END_DATE;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_POSITION;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_START_DATE;
import static com.dislinkt.accountsapi.constants.WorkConstants.NEW_WORK_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.work.Work;
import com.dislinkt.accountsapi.service.work.WorkService;
import com.dislinkt.accountsapi.web.rest.account.payload.WorkDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewWorkRequest;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
public class WorkServiceIntegrationTest {

	
	@Autowired
	private WorkService service;
	
	@Test
	public void testFindByUuid() throws Exception {
		
		Work found = service.findOneByUuidOrElseThrowException(DB_WORK_UUID);
		
		assertEquals(DB_WORK_ACCOUNT_ID, found.getId()); 
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
		Account acc = new Account();
		acc.setUuid(DB_WORK_ACCOUNT_UUID);
		WorkDTO dto = service.insertWorkExperience(req, acc);
		
		assertEquals(NEW_WORK_COMPANY_NAME, dto.getCompanyName());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteWork() {
		
		WorkDTO dto = service.deleteWorkExperience(DB_WORK_UUID);

		assertEquals(DB_WORK_UUID, dto.getUuid());
	}
}
