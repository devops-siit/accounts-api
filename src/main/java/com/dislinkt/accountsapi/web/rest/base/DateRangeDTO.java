package com.dislinkt.accountsapi.web.rest.base;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DateRangeDTO {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

}
