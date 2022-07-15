package com.dislinkt.accountsapi.domain.account;

import com.dislinkt.accountsapi.domain.base.BaseEntity;
import com.dislinkt.accountsapi.domain.follow.Follow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account extends BaseEntity {

    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String username;

    @Embedded
    private Profile profile;

    @NotNull
    @Min(0)
    private Integer followingCount = 0;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY)
    private Set<Follow> following;

    @NotNull
    @Min(0)
    private Integer followersCount = 0;

    @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
    private Set<Follow> followers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "blocked_accounts",
            uniqueConstraints = @UniqueConstraint(name = "UniqueBlock",
                    columnNames = {"target", "blocked"}),
            joinColumns = @JoinColumn(name = "target"),
            inverseJoinColumns = @JoinColumn(name = "blocked"))
    private Set<Account> blockedAccounts;
}
