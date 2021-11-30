package com.exchange.exchangemanagement.rest.contract.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExchangeListRequest {

    private int transactionId;
    private LocalDate conversionDate;
}
