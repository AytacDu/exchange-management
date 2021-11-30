package com.exchange.exchangemanagement.rest.contract.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateRequest {

    private String baseCurrency;
    private String targetCurrency;
}
