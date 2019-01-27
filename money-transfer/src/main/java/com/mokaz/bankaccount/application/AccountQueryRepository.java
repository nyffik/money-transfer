package com.mokaz.bankaccount.application;

public interface AccountQueryRepository {
    AccountResource findByAggregateId(String aggregateId);

    void store(AccountResource resource);

}
