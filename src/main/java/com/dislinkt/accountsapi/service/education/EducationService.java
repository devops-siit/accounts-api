package com.dislinkt.accountsapi.service.education;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.education.Education;
import com.dislinkt.accountsapi.domain.account.work.Work;
import com.dislinkt.accountsapi.domain.base.DateRange;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.EducationRepository;
import com.dislinkt.accountsapi.web.rest.account.payload.EducationDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.WorkDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewEducationRequest;
import com.dislinkt.accountsapi.web.rest.base.DateRangeDTO;

@Service
public class EducationService {

	@Autowired
	private EducationRepository educationRepository;
	
	
	public Education findOneByUuidOrElseThrowException(String uuid) {
        return educationRepository.findOneByUuid(uuid).orElseThrow(() ->
                new EntityNotFoundException("Education not found"));
    }
	
	public EducationDTO insertEducation(NewEducationRequest request, Account acc) {
		
		Education education = new Education();
        education.setName(request.getName());
        education.setDescription(request.getDescription());
        education.setTitle(request.getTitle());
        DateRange duration = new DateRange();
        duration.setStartDate(request.getDuration().getStartDate());
        duration.setEndDate(request.getDuration().getEndDate());
        education.setDuration(duration);
        education.setUuid(education.getUuid());
        education.setAccount(acc);
        
        educationRepository.save(education);
        DateRangeDTO dateRange = new DateRangeDTO();
        dateRange.setEndDate(education.getDuration().getEndDate());
        dateRange.setStartDate(education.getDuration().getStartDate());
        
        EducationDTO dto = new EducationDTO();
        dto.setDescription(education.getDescription());
        dto.setName(education.getName());
        dto.setTitle(education.getTitle());
        dto.setUuid(education.getUuid());
        dto.setDuration(dateRange);
        return dto;
	}
	
	public Set<EducationDTO> toDTOset (Set<Education> entites){
		Set<EducationDTO> retVal = new HashSet<EducationDTO>();
		for (Education e: entites) {
			EducationDTO dto = new EducationDTO();
			DateRangeDTO duration = new DateRangeDTO();
			duration.setEndDate(e.getDuration().getEndDate());
			duration.setStartDate(e.getDuration().getStartDate());
			dto.setDescription(e.getDescription());
			dto.setName(e.getName());
			dto.setTitle(e.getTitle());
			dto.setUuid(e.getUuid());
			dto.setDuration(duration);
			retVal.add(dto);
		}
		return retVal;
	}

	public EducationDTO deleteEducation(String uuid) {
		
		Education existing = findOneByUuidOrElseThrowException(uuid);
		educationRepository.delete(existing);
		EducationDTO retVal = new EducationDTO();
		DateRangeDTO dateRange = new DateRangeDTO();
        dateRange.setEndDate(existing.getDuration().getEndDate());
        dateRange.setStartDate(existing.getDuration().getStartDate());
        
		retVal.setName(existing.getName());
		retVal.setDescription(existing.getDescription());
		retVal.setTitle(existing.getTitle());
		retVal.setDuration(dateRange);
		retVal.setUuid(existing.getUuid());
		
		return retVal;
		
	}
}
