package com.exchange.exchangemanagement.rest.contract.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRequest {
    private Double sourceAmount;
    private String sourceCurrency;
    private String targetCurrency;

}
