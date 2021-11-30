package com.exchange.exchangemanagement.rest.contract.model;

import com.exchange.exchangemanagement.data.model.ExchangeEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeListResponse {
    private List<ExchangeEntity>entityList;
}
