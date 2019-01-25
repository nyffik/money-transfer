package com.mokaz.bankaccount.application;

import com.google.common.eventbus.EventBus;
import com.mokaz.bankaccount.adapter.db.events.CreatedAccountEventSubscriber;
import com.mokaz.bankaccount.adapter.db.events.GuavaEventPublisher;
import com.mokaz.bankaccount.adapter.storage.DomainEventStoreRepositoryImpl;
import com.mokaz.bankaccount.domain.Account;
import com.mokaz.bankaccount.domain.AggregateService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Test
    void shoudl_create(){
        EventBus eventBus = new EventBus();
        eventBus.register(new CreatedAccountEventSubscriber());
        AccountService accountService = new AccountService(new AggregateService(new DomainEventStoreRepositoryImpl(), new GuavaEventPublisher(eventBus)), () -> "1");

        accountService.create("john", "account1");

        Account load = accountService.load("1");

        load.deposit(BigDecimal.valueOf(100));
        accountService.store(load);
        load.deposit(BigDecimal.valueOf(200));
        accountService.store(load);
        load.withdraw(BigDecimal.valueOf(150));
        accountService.store(load);

        Account load1 = accountService.load("1");
        assertThat(load1.getAmount()).isEqualTo("150");

    }

}