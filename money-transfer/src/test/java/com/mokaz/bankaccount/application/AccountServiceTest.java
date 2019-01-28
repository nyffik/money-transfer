package com.mokaz.bankaccount.application;

import com.google.common.eventbus.EventBus;
import com.mokaz.bankaccount.adapter.db.events.CreatedAccountEventSubscriber;
import com.mokaz.bankaccount.adapter.db.events.DepositAccountEventSubscriber;
import com.mokaz.bankaccount.adapter.db.events.GuavaEventPublisher;
import com.mokaz.bankaccount.adapter.db.events.WithdrawAcountEventSubscirber;
import com.mokaz.bankaccount.adapter.db.query.AccountQueryRepositoryImpl;
import com.mokaz.bankaccount.adapter.storage.DomainEventStoreRepositoryImpl;
import com.mokaz.bankaccount.domain.Account;
import com.mokaz.bankaccount.domain.AggregateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    AccountQueryService accountQueryService;
    AccountService accountService;
    @Mock
    UIDGenerator uidGenerator;

    @BeforeEach
    void setUp(){
        AccountQueryRepositoryImpl accountQueryRepository = new AccountQueryRepositoryImpl();
        EventBus eventBus = new EventBus();
        eventBus.register(new CreatedAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new DepositAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new WithdrawAcountEventSubscirber(accountQueryRepository));
         when(uidGenerator.generate()).thenReturn("1");
        accountService =  new AccountService(new AggregateService(new DomainEventStoreRepositoryImpl(), new GuavaEventPublisher(eventBus)), uidGenerator);

       accountQueryService  = new AccountQueryService(accountQueryRepository);
    }

    @Test
    void should_create_account(){

        accountService.create("John", "account1");


        AccountResource load = accountQueryService.load("1");
        assertThat(load.getOwnerName()).isEqualTo("John");
        assertThat(load.getName()).isEqualTo("account1");
        assertThat(load.getAmount()).isEqualTo("0");
        /*Account load = accountService.load("1");

        load.deposit(BigDecimal.valueOf(100));
        accountService.store(load);
        load.deposit(BigDecimal.valueOf(200));
        accountService.store(load);
        load.withdraw(BigDecimal.valueOf(150));
        accountService.store(load);

        Account load1 = accountService.load("1");
        assertThat(load1.getAmount()).isEqualTo("150");
*/
    }

    @Test
    void should_deposit(){
        accountService.create("John", "account1");

        accountService.deposit("1", BigDecimal.valueOf(100));

        AccountResource load = accountQueryService.load("1");
        assertThat(load.getOwnerName()).isEqualTo("John");
        assertThat(load.getName()).isEqualTo("account1");
        assertThat(load.getAmount()).isEqualTo("100");
    }

    @Test
    void should_withdraw(){
        accountService.create("John", "account1");

        accountService.deposit("1", BigDecimal.valueOf(100));


        accountService.withdraw("1", BigDecimal.valueOf(50));

        AccountResource load = accountQueryService.load("1");
        assertThat(load.getOwnerName()).isEqualTo("John");
        assertThat(load.getName()).isEqualTo("account1");
        assertThat(load.getAmount()).isEqualTo("50");
    }

    @Test
    void shouldTransferMoenyFromAccount(){
        when(uidGenerator.generate()).thenReturn("1").thenReturn("2");

        accountService.create("John", "account1");
        accountService.deposit("1", BigDecimal.valueOf(300));


        accountService.create("Mark", "account2");
        accountService.deposit("2", BigDecimal.valueOf(200));


        accountService.transfer("1", "2", BigDecimal.valueOf(250));

        AccountResource load = accountQueryService.load("1");
        assertThat(load.getOwnerName()).isEqualTo("John");
        assertThat(load.getName()).isEqualTo("account1");
        assertThat(load.getAmount()).isEqualTo("50");

        AccountResource load2 = accountQueryService.load("2");
        assertThat(load2.getOwnerName()).isEqualTo("Mark");
        assertThat(load2.getName()).isEqualTo("account2");
        assertThat(load2.getAmount()).isEqualTo("450");

    }

}