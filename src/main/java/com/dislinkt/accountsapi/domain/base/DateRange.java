package com.dislinkt.accountsapi.domain.base;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Embeddable
@Data
public class DateRange {

    @PastOrPresent
    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;
}
