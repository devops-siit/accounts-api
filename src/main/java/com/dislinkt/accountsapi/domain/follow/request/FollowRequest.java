package com.dislinkt.accountsapi.domain.follow.request;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "SourceTargetUnique", columnNames = {"source_id", "target_id"})})
public class FollowRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private Account targetAccount;
}
