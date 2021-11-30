package com.exchange.exchangemanagement.rest.contract.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeResponse {

    private double targetAmount;
    private int transactionId;
}
