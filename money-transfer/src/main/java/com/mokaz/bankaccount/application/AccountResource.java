package com.mokaz.bankaccount.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class AccountResource {
    @NonNull
    private final  String aggregateId;
    private String ownerName;
    private String name;
    private BigDecimal amount;

    public void deposit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }
}
