package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountQueryService;
import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.application.AccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountQueryService accountQueryService;

    @Get()
    public List<AccountResource> index() {
        return accountQueryService.findAll();
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public void create(@Body CreateAccountDto createAccountDto){
        accountService.create(createAccountDto.getOwnerName(),createAccountDto.getAccountName());
    }

    @Post(value="/deposit", consumes = MediaType.APPLICATION_JSON)
    public void deposit(@Body DepositDto depositDto){
        accountService.deposit(depositDto.getAggregateId(), new BigDecimal(depositDto.getAmount()));
    }

    @Post(value = "/transfer",consumes = MediaType.APPLICATION_JSON)
    public void transfer(@Body TransferDto transferDto){
        accountService.transfer(transferDto.getAggregateIfFrom(),transferDto.getAggregateIdTo(), new BigDecimal(transferDto.getAmount()));
    }
}
