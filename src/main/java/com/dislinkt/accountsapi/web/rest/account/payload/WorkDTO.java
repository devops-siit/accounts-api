package com.dislinkt.accountsapi.web.rest.account.payload;

import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

import lombok.Data;

@Data
public class WorkDTO extends BaseDTO{
	
	private String companyName;
	
	private String position;
	
	private String description;
	
	private DateRangeDTO duration;

}
