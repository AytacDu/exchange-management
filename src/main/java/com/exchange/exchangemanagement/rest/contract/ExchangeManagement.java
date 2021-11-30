package com.exchange.exchangemanagement.rest.contract;

import com.exchange.exchangemanagement.rest.contract.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping(value = "/exchange-management")
public interface  ExchangeManagement {

    @PostMapping(value = "/getExchangeRate")
    ResponseEntity<ExchangeRateResponse> retrieveRate (@RequestBody ExchangeRateRequest exchangeRateRequest);

    @PostMapping(value = "/exchange")
    ResponseEntity<ExchangeResponse> exchange (@RequestBody ExchangeRequest exchangeRequest);

    @PostMapping(value = "/getExchangeList")
    ResponseEntity<ExchangeListResponse> getExchangeList (@RequestBody ExchangeListRequest exchangeListRequest);
}
