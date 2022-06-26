package com.dislinkt.accountsapi.domain.account.work;

import com.dislinkt.accountsapi.domain.base.BaseEntity;
import com.dislinkt.accountsapi.domain.base.DateRange;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
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
}
