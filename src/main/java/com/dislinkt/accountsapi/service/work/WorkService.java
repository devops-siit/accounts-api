package com.dislinkt.accountsapi.service.work;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.work.Work;
import com.dislinkt.accountsapi.domain.base.DateRange;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.WorkRepository;
import com.dislinkt.accountsapi.web.rest.account.payload.WorkDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewWorkRequest;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

@Service
public class WorkService {
	
	@Autowired
	private WorkRepository workRepository;
	
	public Work findOneByUuidOrElseThrowException(String uuid) {
        return workRepository.findOneByUuid(uuid).orElseThrow(() ->
                new EntityNotFoundException("Work not found"));
    }
	
	public WorkDTO insertWorkExperience(NewWorkRequest request, Account acc) {
		
		Work work = new Work();
		work.setCompanyName(request.getCompanyName());
        work.setDescription(request.getDescription());
        work.setPosition(request.getPosition());
        DateRange duration = new DateRange();
        duration.setStartDate(request.getDuration().getStartDate());
        duration.setEndDate(request.getDuration().getEndDate());
        work.setDuration(duration);
        work.setUuid(work.getUuid());
        work.setAccount(acc);
        
        workRepository.save(work);
        DateRangeDTO dateRange = new DateRangeDTO();
        dateRange.setEndDate(work.getDuration().getEndDate());
        dateRange.setStartDate(work.getDuration().getStartDate());
        
        WorkDTO dto = new WorkDTO();
        dto.setDescription(work.getDescription());
        dto.setCompanyName(work.getCompanyName());
        dto.setPosition(work.getPosition());
        dto.setUuid(work.getUuid());
        dto.setDuration(dateRange);
        return dto;
	}
	
	public WorkDTO deleteWorkExperience(String uuid) {
		
		Work existing = findOneByUuidOrElseThrowException(uuid);
		workRepository.delete(existing);
		WorkDTO retVal = new WorkDTO();
		DateRangeDTO dateRange = new DateRangeDTO();
        dateRange.setEndDate(existing.getDuration().getEndDate());
        dateRange.setStartDate(existing.getDuration().getStartDate());
		retVal.setCompanyName(existing.getCompanyName());
		retVal.setDescription(existing.getDescription());
		retVal.setPosition(existing.getPosition());
		retVal.setDuration(dateRange);
		retVal.setUuid(existing.getUuid());
		
		return retVal;
	}
	
	public Set<WorkDTO> toDTOset (Set<Work> entites){
		Set<WorkDTO> retVal = new HashSet<WorkDTO>();
		for (Work w: entites) {
			WorkDTO dto = new WorkDTO();
			DateRangeDTO duration = new DateRangeDTO();
			duration.setEndDate(w.getDuration().getEndDate());
			duration.setStartDate(w.getDuration().getStartDate());
			dto.setDescription(w.getDescription());
			dto.setCompanyName(w.getCompanyName());
			dto.setPosition(w.getPosition());
			dto.setUuid(w.getUuid());
			dto.setDuration(duration);
			retVal.add(dto);
		}
		return retVal;
	}

}
