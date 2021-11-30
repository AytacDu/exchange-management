package com.exchange.exchangemanagement.data.impl;

import com.exchange.exchangemanagement.data.model.ExchangeEntity;
import com.exchange.exchangemanagement.data.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeDaoServiceImpl {

    private ExchangeRepository exchangeRepository;

    @Autowired
    public ExchangeDaoServiceImpl(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public ExchangeEntity getConversionById(int transactionId) {
        return exchangeRepository.findById(transactionId).get();
    }

    public void saveOrUpdate(ExchangeEntity exchangeEntity) {
        exchangeRepository.save(exchangeEntity);
    }



}
