package com.dislinkt.accountsapi.domain.account;

import com.dislinkt.accountsapi.domain.base.BaseEntity;
import com.dislinkt.accountsapi.domain.follow.Follow;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
public class Account extends BaseEntity {

    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String username;

    @Embedded
    private Profile profile;

    @NotNull
    @Min(0)
    private Integer followingCount;

    @OneToMany(mappedBy = "source")
    private Set<Follow> following;

    @NotNull
    @Min(0)
    private Integer followersCount;

    @OneToMany(mappedBy = "target")
    private Set<Follow> followers;

    @ManyToMany
    @JoinTable(name = "blocked_accounts",
            uniqueConstraints = @UniqueConstraint(name = "UniqueBlock",
                    columnNames = {"target", "blocked"}),
            joinColumns = @JoinColumn(name = "target"),
            inverseJoinColumns = @JoinColumn(name = "blocked"))
    private Set<Account> blockedAccounts;
}
