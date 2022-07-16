package com.dislinkt.accountsapi.service.accounts;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.account.Profile;
import com.dislinkt.accountsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.repository.AccountRepository;
import com.dislinkt.accountsapi.web.rest.account.payload.AccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.SimpleAccountDTO;
import com.dislinkt.accountsapi.web.rest.account.payload.request.EditProfileRequest;
import com.dislinkt.accountsapi.web.rest.account.payload.request.NewAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public AccountDTO insertAccount(NewAccountRequest request) {
        Optional<Account> accountOrEmpty = accountRepository.findOneByUsername(request.getUsername());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account username already exist");
        }

        accountOrEmpty = accountRepository.findOneByProfileEmail(request.getEmail());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account email already exist");
        }

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setUuid(request.getUuid());

        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setEmail(request.getEmail());
        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setPhone(request.getPhone());

        account.setProfile(profile);
        accountRepository.save(account);

        SimpleAccountDTO simpleAccountDTO = new SimpleAccountDTO();
        simpleAccountDTO.setName(account.getProfile().getName());
        simpleAccountDTO.setUsername(account.getUsername());
        simpleAccountDTO.setUuid(account.getUuid());

        HttpEntity<SimpleAccountDTO> accountRequest = new HttpEntity<>(simpleAccountDTO);

        ResponseEntity<SimpleAccountDTO> responseFromPosts =
                restTemplate.exchange("http://POSTS-API/accounts",
                        HttpMethod.POST,
                        accountRequest,
                        SimpleAccountDTO.class);

        ResponseEntity<SimpleAccountDTO> responseFromChats =
                restTemplate.exchange("http://CHAT-API/accounts",
                        HttpMethod.POST,
                        accountRequest,
                        SimpleAccountDTO.class);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUuid(account.getUuid());
        accountDTO.setEmail(account.getProfile().getEmail());
        accountDTO.setGender(account.getProfile().getGender());
        accountDTO.setPhone(account.getProfile().getPhone());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
        accountDTO.setName(account.getProfile().getName());

        return accountDTO;
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
            accountDTO.setDateOfBirth(account.getProfile().getDateOfBirth());
            accountDTO.setName(account.getProfile().getName());
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
        accountDTO.setFollowersCount(account.getFollowersCount());
        accountDTO.setFollowingCount(account.getFollowingCount());

        return accountDTO;
    }

    public Account findByUuidOrElseThrowException(String uuid) {
        return accountRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public AccountDTO editProfile(String accountUuid, EditProfileRequest request) {
        Optional<Account> accountOrEmpty = accountRepository.findByUuid(accountUuid);

        if (accountOrEmpty.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        Account account = accountOrEmpty.get();

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

        return accountDTO;
    }
}

