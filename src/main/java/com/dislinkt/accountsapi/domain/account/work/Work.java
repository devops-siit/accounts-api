package com.dislinkt.accountsapi.domain.account.work;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.base.BaseEntity;
import com.dislinkt.accountsapi.domain.base.DateRange;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Work extends BaseEntity {

    @NotNull
    @Size(max = 128)
    private String companyName;

    @NotNull
    @Size(max = 128)
    private String position;

    @Size(max = 1028)
    private String description;

    @Embedded
    private DateRange duration;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
