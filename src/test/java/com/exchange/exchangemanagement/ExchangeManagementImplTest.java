package com.exchange.exchangemanagement;

import com.exchange.exchangemanagement.data.impl.ExchangeDaoServiceImpl;
import com.exchange.exchangemanagement.data.model.ExchangeEntity;
import com.exchange.exchangemanagement.data.repository.ExchangeRepository;
import com.exchange.exchangemanagement.model.ConvertResponse;
import com.exchange.exchangemanagement.model.LatestRatesResponse;
import com.exchange.exchangemanagement.rest.contract.model.*;
import com.exchange.exchangemanagement.rest.impl.ExchangeManagementImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExchangeManagementImplTest {

    private ExchangeManagementImpl exchangeManagementUnderTest;
    private ExchangeResponse exchangeResponse;
    private ExchangeRateResponse exchangeRateResponse;
    private ExchangeRateRequest exchangeRateRequest;
    private LatestRatesResponse latestRatesResponse;
    private ConvertResponse convertResponse;
    private ExchangeRequest exchangeRequest;
    private ExchangeListRequest exchangeListRequest;
    private ExchangeEntity exchangeEntity;
    private List<ExchangeEntity> entityList;

    @Mock
    private ExchangeDaoServiceImpl mockExchangeDaoService;

    @Mock
    private ExchangeRepository mockExchangeRepository;

    @Mock
    private @Qualifier("restTemplate")
    RestTemplate mockRestTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        convertResponse = new ConvertResponse();
        exchangeEntity = new ExchangeEntity();
        entityList = new ArrayList<>();
        exchangeListRequest = new ExchangeListRequest();
        exchangeRateRequest = new ExchangeRateRequest();
        latestRatesResponse = new LatestRatesResponse();
        exchangeRateRequest = new ExchangeRateRequest();
        exchangeRateResponse = new ExchangeRateResponse();
        exchangeRequest = new ExchangeRequest();

        initValues();
        exchangeManagementUnderTest = new ExchangeManagementImpl(mockRestTemplate, mockExchangeDaoService,mockExchangeRepository);
    }



    @Test
    public void testRetrieveRate() {
        when(mockRestTemplate.getForObject(any(String.class), any())).thenReturn(latestRatesResponse);

        final ResponseEntity<ExchangeRateResponse> result = exchangeManagementUnderTest.retrieveRate(exchangeRateRequest);

        verify(mockRestTemplate, atLeastOnce()).getForObject(any(String.class), any());
        Assert.assertNotNull(result.getBody().getExchangeRate());
    }

    @Test
    public void testExchange() {
        when(mockRestTemplate.getForObject(any(String.class), any())).thenReturn(convertResponse);
        doNothing().when(mockExchangeDaoService).saveOrUpdate(any());

        final ResponseEntity<ExchangeResponse> result = exchangeManagementUnderTest.exchange(exchangeRequest);

        verify(mockRestTemplate, atLeastOnce()).getForObject(any(String.class), any());
        verify(mockExchangeDaoService, atLeastOnce()).saveOrUpdate(any());

        result.getBody();
    }

    @Test
    public void testGetExchangeList() {
        when(mockExchangeDaoService.getConversionById(anyInt())).thenReturn(exchangeEntity);
        when(mockExchangeRepository.listConvByDate(any(LocalDate.class))).thenReturn(entityList);
        exchangeListRequest.setTransactionId(1);
        final ResponseEntity<ExchangeListResponse> result = exchangeManagementUnderTest.getExchangeList(exchangeListRequest);

        verify(mockExchangeDaoService, atLeastOnce()).getConversionById(anyInt());
        Assert.assertTrue(1.0 == result.getBody().getEntityList().get(0).getTargetAmount());

        exchangeListRequest.setConversionDate(LocalDate.MIN);
        exchangeListRequest.setTransactionId(0);
        final ResponseEntity<ExchangeListResponse> resultEntity = exchangeManagementUnderTest.getExchangeList(exchangeListRequest);
        verify(mockExchangeDaoService, atLeastOnce()).getConversionById(anyInt());
        Assert.assertTrue(1.0 == resultEntity.getBody().getEntityList().get(1).getTargetAmount());

    }

    private void initValues() {
        convertResponse.setDate(LocalDate.MIN);
        convertResponse.setResult(1.00);
        convertResponse.setSuccess(true);
        convertResponse.setTimestamp(123456);

        exchangeRequest.setSourceAmount(1.0);
        exchangeRequest.setSourceCurrency("EUR");
        exchangeRequest.setTargetCurrency("TRY");

        exchangeRateRequest.setBaseCurrency("EUR");
        exchangeRateRequest.setTargetCurrency("TRY");

        latestRatesResponse.setBase("EUR");
        latestRatesResponse.setSuccess(true);
        latestRatesResponse.setTimestamp(123123);
        HashMap<String, Double> hashMap = new HashMap<>();
        hashMap.put("TRY", 1.0);
        latestRatesResponse.setRates(hashMap);

        exchangeEntity.setTransactionId(1);
        exchangeEntity.setTargetAmount(1.0);
        exchangeEntity.setTargetCurrency("TRY");
        exchangeEntity.setSourceCurrency("EUR");
        exchangeEntity.setSourceAmount(1.0);
        exchangeEntity.setDate(LocalDate.MIN);

        entityList.add(exchangeEntity);
        exchangeEntity = new ExchangeEntity();
        exchangeEntity.setTransactionId(2);
        exchangeEntity.setTargetAmount(1.0);
        exchangeEntity.setTargetCurrency("TRY");
        exchangeEntity.setSourceCurrency("EUR");
        exchangeEntity.setSourceAmount(1.0);
        exchangeEntity.setDate(LocalDate.MIN);
        entityList.add(exchangeEntity);
    }
}
