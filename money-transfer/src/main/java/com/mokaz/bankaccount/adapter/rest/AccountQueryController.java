package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountQueryService;
import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("/account")
@RequiredArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @Get
    public Single<List<AccountResource>> findAll() {
        List<AccountResource> all = accountQueryService.findAll();
        all.forEach(System.out::println);
        return Observable.fromIterable(all).toList();
    }
}
