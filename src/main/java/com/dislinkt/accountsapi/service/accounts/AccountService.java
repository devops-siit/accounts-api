package com.dislinkt.accountsapi.service.accounts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dislinkt.accountsapi.event.AccountCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.Profile;
import com.dislinkt.accountsapi.domain.account.education.Education;
import com.dislinkt.accountsapi.domain.account.work.Work;
import com.dislinkt.accountsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.service.education.EducationService;
import com.dislinkt.accountsapi.service.work.WorkService;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.EducationDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.WorkDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewEducationRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewWorkRequest;

@Service
@EnableBinding(Sink.class)
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private EducationService educationService;
    
    @Autowired
    private WorkService workService;

    @StreamListener(target = Sink.INPUT)
    public void insertAccount(AccountCreatedEvent event) {
        Optional<Account> accountOrEmpty = accountRepository.findOneByUsername(event.getUsername());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account username already exist");
        }

        accountOrEmpty = accountRepository.findOneByProfileEmail(event.getEmail());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account email already exist");
        }

        Account account = new Account();
        account.setUsername(event.getUsername());
        account.setUuid(event.getUuid());

        Profile profile = new Profile();
        profile.setName(event.getName());
        profile.setEmail(event.getEmail());
        profile.setGender(event.getGender());
        profile.setDateOfBirth(event.getDateOfBirth());
        profile.setPhone(event.getPhone());

        account.setProfile(profile);
        accountRepository.save(account);
    }

    public Page<AccountDTO> findByUsernameContainsOrNameContains(String pattern, Pageable pageable) {
        return accountRepository.findByUsernameContainsOrProfileNameContains(pattern,
                pattern,
                pageable).map(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUsername(account.getUsername());
            accountDTO.setUuid(account.getUuid());
            accountDTO.setEmail(account.getProfile().getEmail());
            accountDTO.setGender(account.getProfile().getGender());
            accountDTO.setPhone(account.getProfile().getPhone());
            accountDTO.setUsername(accountDTO.getUsername());
            accountDTO.setIsPublic(account.getProfile().getIsPublic());
            accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
            accountDTO.setName(account.getProfile().getName());
            accountDTO.setBiography(account.getProfile().getBiography());
            accountDTO.setFollowersCount(account.getFollowersCount());
            accountDTO.setFollowingCount(account.getFollowingCount());

            return accountDTO;
        });
    }

    public Page<AccountDTO> publicSearch(String pattern, Pageable pageable) {
        return accountRepository.findByUsernameContainsOrProfileNameContainsAndProfileIsPublicEquals(pattern,
                pattern, true,
                pageable).map(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUsername(account.getUsername());
            accountDTO.setUuid(account.getUuid());
            accountDTO.setEmail(account.getProfile().getEmail());
            accountDTO.setGender(account.getProfile().getGender());
            accountDTO.setPhone(account.getProfile().getPhone());
            accountDTO.setUsername(accountDTO.getUsername());
            accountDTO.setIsPublic(account.getProfile().getIsPublic());
            accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
            accountDTO.setName(account.getProfile().getName());
            accountDTO.setBiography(account.getProfile().getBiography());
            accountDTO.setFollowersCount(account.getFollowersCount());
            accountDTO.setFollowingCount(account.getFollowingCount());

            return accountDTO;
        });
    }

    public AccountDTO findDTOByUuidOrElseThrowException(String uuid) {
        Account account = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUuid(account.getUuid());
        accountDTO.setEmail(account.getProfile().getEmail());
        accountDTO.setGender(account.getProfile().getGender());
        accountDTO.setPhone(account.getProfile().getPhone());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
        accountDTO.setName(account.getProfile().getName());
        accountDTO.setIsPublic(account.getProfile().getIsPublic());
        accountDTO.setBiography(account.getProfile().getBiography());
        accountDTO.setFollowersCount(account.getFollowersCount());
        accountDTO.setFollowingCount(account.getFollowingCount());
        
        // set educations
        if (account.getProfile().getEducation() != null)
        	accountDTO.setEducation(educationService.toDTOset(account.getProfile().getEducation()));
        // set work experience
        if (account.getProfile().getWorkExperience() != null)
        	accountDTO.setWorkExperience(workService.toDTOset(account.getProfile().getWorkExperience()));

        return accountDTO;
    }

    public Account findByUuidOrElseThrowException(String uuid) {
        return accountRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public AccountDTO editProfile(EditProfileRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());

        account.getProfile().setName(request.getName());
        account.getProfile().setEmail(request.getEmail());
        account.getProfile().setGender(request.getGender());
        account.getProfile().setBiography(request.getBiography());
        account.getProfile().setDateOfBirth(request.getDateOfBirth());
        account.getProfile().setIsPublic(request.getIsPublic());

        accountRepository.save(account);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUuid(account.getUuid());
        accountDTO.setEmail(account.getProfile().getEmail());
        accountDTO.setGender(account.getProfile().getGender());
        accountDTO.setPhone(account.getProfile().getPhone());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
        accountDTO.setName(account.getProfile().getName());
        accountDTO.setFollowersCount(account.getFollowersCount());
        accountDTO.setFollowingCount(account.getFollowingCount());
        accountDTO.setBiography(account.getProfile().getBiography());
        accountDTO.setIsPublic(account.getProfile().getIsPublic());

        return accountDTO;
    }
    
    public AccountDTO insertEducation(NewEducationRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());

        EducationDTO savedEducation = educationService.insertEducation(request, account);
        
        Education education = educationService.findOneByUuidOrElseThrowException(savedEducation.getUuid()); 
        account.getProfile().addEducation(education);
        accountRepository.save(account);
        
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUuid(account.getUuid());
        accountDTO.setEmail(account.getProfile().getEmail());
        accountDTO.setGender(account.getProfile().getGender());
        accountDTO.setPhone(account.getProfile().getPhone());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
        accountDTO.setName(account.getProfile().getName());
        accountDTO.setIsPublic(account.getProfile().getIsPublic());
        accountDTO.setBiography(account.getProfile().getBiography());
        accountDTO.setFollowersCount(account.getFollowersCount());
        accountDTO.setFollowingCount(account.getFollowingCount());
        
        accountDTO.setEducation(educationService.toDTOset(account.getProfile().getEducation()));
        accountDTO.setWorkExperience(workService.toDTOset(account.getProfile().getWorkExperience()));

        
    	return accountDTO;
    }
    
    
    public AccountDTO insertWork(NewWorkRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());
        WorkDTO savedWork = workService.insertWorkExperience(request, account);
        
        Work work = workService.findOneByUuidOrElseThrowException(savedWork.getUuid()); 
        account.getProfile().addWorkExperience(work);
        accountRepository.save(account);
        
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUuid(account.getUuid());
        accountDTO.setEmail(account.getProfile().getEmail());
        accountDTO.setGender(account.getProfile().getGender());
        accountDTO.setPhone(account.getProfile().getPhone());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setIsPublic(account.getProfile().getIsPublic());
        accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
        accountDTO.setName(account.getProfile().getName());
        accountDTO.setBiography(account.getProfile().getBiography());
        accountDTO.setFollowersCount(account.getFollowersCount());
        accountDTO.setFollowingCount(account.getFollowingCount());
        
        accountDTO.setEducation(educationService.toDTOset(account.getProfile().getEducation()));
        accountDTO.setWorkExperience(workService.toDTOset(account.getProfile().getWorkExperience()));
        
    	return accountDTO;
    }

	public AccountDTO deleteEducation(String uuid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());
        
        educationService.deleteEducation(uuid); 
        
        AccountDTO accDTO = findDTOByUuidOrElseThrowException(account.getUuid());

        return accDTO;
	}

	public AccountDTO deleteWorkExperience(String uuid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());
        workService.deleteWorkExperience(uuid); 
        
        AccountDTO accDTO = findDTOByUuidOrElseThrowException(account.getUuid());

        return accDTO;
	}

    public Account findOneByUsernameOrThrowNotFoundException(String username) {
        return accountRepository.findOneByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public Account findOneByUuidOrThrowNotFoundException(String uuid) {
        return accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public Optional<Account> findOneByUsername(String username) {
        return accountRepository.findOneByUsername(username);
    }

    public List<AccountDTO> findAllBlockingAccounts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());

        return account.getBlockedAccounts().stream().map(acc -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUsername(acc.getUsername());
            accountDTO.setUuid(acc.getUuid());
            accountDTO.setEmail(acc.getProfile().getEmail());
            accountDTO.setGender(acc.getProfile().getGender());
            accountDTO.setPhone(acc.getProfile().getPhone());
            accountDTO.setUsername(acc.getUsername());
            accountDTO.setDateOfBirth(acc.getProfile().getDateOfBirth());
            accountDTO.setBiography(acc.getProfile().getBiography());
            accountDTO.setName(acc.getProfile().getName());
            accountDTO.setFollowersCount(acc.getFollowersCount());
            accountDTO.setFollowingCount(acc.getFollowingCount());

            return accountDTO;
        }).collect(Collectors.toList());
    }

    public void blockAccount(String accountUuid) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Account blockAccount = findOneByUuidOrThrowNotFoundException(accountUuid);

        if (blockAccount.getUuid().equals(account.getUuid())) {
            throw new EntityAlreadyExistsException("Can't block yourself");
        }

        account.getBlockedAccounts().add(blockAccount);
        accountRepository.save(account);
    }

    public void unblockAccount(String accountUuid) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = findOneByUsernameOrThrowNotFoundException(user.getUsername());

        Account blockAccount = findOneByUuidOrThrowNotFoundException(accountUuid);

        if (!account.getBlockedAccounts().contains(blockAccount)) {
            throw new EntityNotFoundException("Account is not blocked");
        }

        account.getBlockedAccounts().remove(blockAccount);

        accountRepository.save(account);
    }
}

