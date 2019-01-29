package com.mokaz.bankaccount.application;

import com.google.common.eventbus.EventBus;
import com.mokaz.bankaccount.adapter.db.events.CreatedAccountEventSubscriber;
import com.mokaz.bankaccount.adapter.db.events.DepositAccountEventSubscriber;
import com.mokaz.bankaccount.adapter.db.events.GuavaEventPublisher;
import com.mokaz.bankaccount.adapter.db.events.WithdrawAcountEventSubscirber;
import com.mokaz.bankaccount.adapter.db.query.AccountQueryRepositoryImpl;
import com.mokaz.bankaccount.adapter.storage.DomainEventStoreRepositoryImpl;
import com.mokaz.bankaccount.domain.AggregateService;
import com.mokaz.bankaccount.domain.ApplicationEventPublisher;
import com.mokaz.bankaccount.domain.DomainEventStoreRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.rmi.server.UID;
import java.util.UUID;

@Factory
public class AccountFactory {

    @Singleton
    @Bean
    AccountService accountService(AggregateService aggregateService){
        return new AccountService(aggregateService,()-> UUID.randomUUID().toString());
    }

    @Singleton
    @Bean
    AggregateService aggregateService(DomainEventStoreRepository repository, ApplicationEventPublisher applicationEventPublisher){
        return new AggregateService(repository, applicationEventPublisher);
    }


    @Singleton
    @Bean
    DomainEventStoreRepository domainEventStoreRepository(){
        return new DomainEventStoreRepositoryImpl();
    }

    @Singleton
    @Bean
    ApplicationEventPublisher applicationEventPublisher(EventBus eventBus){
        return new GuavaEventPublisher(eventBus);
    }

    @Singleton
    @Bean
    EventBus eventBus(AccountQueryRepository accountQueryRepository){
        EventBus eventBus = new EventBus();
        eventBus.register(new CreatedAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new DepositAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new WithdrawAcountEventSubscirber(accountQueryRepository));
        return eventBus;
    }

    @Singleton
    @Bean
    AccountQueryRepository accountQueryRepository(){
        return new AccountQueryRepositoryImpl();
    }

    @Singleton
    @Bean
    AccountQueryService accountQueryService(AccountQueryRepository accountQueryRepository){
        return new AccountQueryService(accountQueryRepository);
    }

}
