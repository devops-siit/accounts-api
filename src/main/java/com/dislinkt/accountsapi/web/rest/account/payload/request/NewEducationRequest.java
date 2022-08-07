package com.dislinkt.accountsapi.web.rest.account.payload.request;

import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

import lombok.Data;

@Data
public class NewEducationRequest extends BaseDTO{

	private String name;
	
	private String title;
	
	private String description;
	
	private DateRangeDTO duration;
}
