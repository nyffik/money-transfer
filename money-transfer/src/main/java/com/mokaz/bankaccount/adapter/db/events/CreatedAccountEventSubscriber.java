package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.Subscribe;
import com.mokaz.bankaccount.domain.AccountCreatedEvent;

public class CreatedAccountEventSubscriber {

    @Subscribe
    public void handle(AccountCreatedEvent event) {
        System.out.println(event.getAccountName());
    }
}
