package com.mokaz.bankaccount.application;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;

    AccountResource load(String aggregateId) {
        return accountQueryRepository.findByAggregateId(aggregateId);
    }
}
