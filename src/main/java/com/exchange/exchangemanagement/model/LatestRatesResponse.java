package com.exchange.exchangemanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashMap;

@Getter
@Setter
public class LatestRatesResponse {

    private boolean success;
    private int timestamp;
    private String base;
    private Date date;
    private HashMap<String, Double> rates;
}
