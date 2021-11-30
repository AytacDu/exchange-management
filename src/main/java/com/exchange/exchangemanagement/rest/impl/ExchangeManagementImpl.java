package com.exchange.exchangemanagement.rest.impl;

import com.exchange.exchangemanagement.data.impl.ExchangeDaoServiceImpl;
import com.exchange.exchangemanagement.data.model.ExchangeEntity;
import com.exchange.exchangemanagement.data.repository.ExchangeRepository;
import com.exchange.exchangemanagement.model.ConvertResponse;
import com.exchange.exchangemanagement.model.LatestRatesResponse;
import com.exchange.exchangemanagement.rest.contract.ExchangeManagement;
import com.exchange.exchangemanagement.rest.contract.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@RestController
public class ExchangeManagementImpl implements ExchangeManagement {

    private RestTemplate restTemplate;
    private ExchangeDaoServiceImpl exchangeDaoService;
    private ExchangeRepository exchangeRepository;
    private static final String URL = "http://data.fixer.io/api";

    @Autowired
    public ExchangeManagementImpl(@Qualifier("restTemplate") RestTemplate restTemplate, ExchangeDaoServiceImpl exchangeDaoService, ExchangeRepository exchangeRepository) {
        this.restTemplate = restTemplate;
        this.exchangeDaoService = exchangeDaoService;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public ResponseEntity<ExchangeRateResponse> retrieveRate(ExchangeRateRequest exchangeRateRequest) {
        StringBuilder sb = new StringBuilder(URL).append("/latest")
                .append("?")
                .append("access=612395eca17d049395e41131226a7318")
                .append("&")
                .append("base=" + exchangeRateRequest.getBaseCurrency());

        LatestRatesResponse latestRatesResponse = restTemplate.getForObject(sb.toString(), LatestRatesResponse.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setExchangeRate(latestRatesResponse.getRates().get(exchangeRateRequest.getTargetCurrency()));

        return ResponseEntity.ok(exchangeRateResponse);

    }

    @Override
    public ResponseEntity<ExchangeResponse> exchange (ExchangeRequest exchangeRequest) {
        StringBuilder sb = new StringBuilder(URL).append("/convert")
                .append("?")
                .append("access=612395eca17d049395e41131226a7318").append("&")
                .append("from=" + exchangeRequest.getSourceCurrency()).append("&")
                .append("to=" + exchangeRequest.getTargetCurrency()).append("&")
                .append("amount=" + exchangeRequest.getSourceAmount());

        ConvertResponse convertResponse = restTemplate.getForObject(sb.toString(), ConvertResponse.class);
        ExchangeEntity exchangeEntity = new ExchangeEntity();
        exchangeEntity.setSourceAmount(exchangeRequest.getSourceAmount());
        exchangeEntity.setSourceCurrency(exchangeRequest.getSourceCurrency());
        exchangeEntity.setTargetCurrency(exchangeRequest.getTargetCurrency());
        exchangeEntity.setTargetAmount(convertResponse.getResult());
        exchangeDaoService.saveOrUpdate(exchangeEntity);

        ExchangeResponse exchangeResponse = new ExchangeResponse();
        exchangeResponse.setTargetAmount(convertResponse.getResult());
        exchangeResponse.setTransactionId(exchangeEntity.getTransactionId());
        return ResponseEntity.ok(exchangeResponse);
    }

    @Override
    public ResponseEntity<ExchangeListResponse> getExchangeList(ExchangeListRequest exchangeListRequest) {
        List<ExchangeEntity> entityList = new ArrayList<>();
        if (exchangeListRequest.getTransactionId() > 0) {
            entityList.add(exchangeDaoService.getConversionById(exchangeListRequest.getTransactionId()));
        } else {
            entityList.addAll(exchangeRepository.listConvByDate(exchangeListRequest.getConversionDate()));
        }
        ExchangeListResponse exchangeListResponse = new ExchangeListResponse();
        exchangeListResponse.setEntityList(entityList);
        return ResponseEntity.ok(exchangeListResponse);
    }




}
