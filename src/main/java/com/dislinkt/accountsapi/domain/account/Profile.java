package com.dislinkt.accountsapi.domain.account;

import com.dislinkt.accountsapi.domain.account.education.Education;
import com.dislinkt.accountsapi.domain.account.work.Work;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Embeddable
public class Profile {

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    @Size(max = 128)
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 36)
    private String phone;

    @NotNull
    @Size(max = 36)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Past
    private LocalDateTime dateOfBirth;

    @Size(max = 1024)
    private String biography;

    @NotNull
    private Boolean isPublic = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "account")
    private Set<Education> education;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "account")
    private Set<Work> workExperience;
    
    public void addEducation(Education education) {
    	this.education.add(education);
    }
    
    public void addWorkExperience(Work experience) {
    	this.workExperience.add(experience);
    }
}
