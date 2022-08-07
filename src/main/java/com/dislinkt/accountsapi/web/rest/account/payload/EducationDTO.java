package com.dislinkt.accountsapi.web.rest.account.payload;

import com.dislinkt.accountsapi.web.rest.base.BaseDTO;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

import lombok.Data;

@Data
public class EducationDTO extends BaseDTO{

	private String name;
	
	private String title;
	
	private String description;
	
	private DateRangeDTO duration;
}
