package com.mokaz.bankaccount.adapter.rest;

import io.micronaut.http.annotation.Get;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDto {
    String aggregateIfFrom;
    String aggregateIdTo;
    String amount;
}
