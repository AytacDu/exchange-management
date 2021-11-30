package com.exchange.exchangemanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConvertResponse {

    private boolean success;
    private QueryEntity query;
    private int timestamp;
    private InfoEntity infoEntity;
    private LocalDate date;
    private double result;


}
