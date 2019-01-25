package com.mokaz.bankaccount.application;

import com.mokaz.bankaccount.domain.Account;
import com.mokaz.bankaccount.domain.AggregateService;
import com.mokaz.bankaccount.domain.DomainEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountService {
    private final AggregateService aggregateService;
    private final UIDGenerator uidGenerator;

    public void create(String ownerName, String name) {
        Account account = Account.from(uidGenerator.generate());
        account.create(ownerName,name);
        store(account);
    }

    public void store(Account account){
        aggregateService.store(account);
    }

    public Account load(String aggregateId) {
        return aggregateService.load(aggregateId);
    }
}
