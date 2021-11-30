package com.exchange.exchangemanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QueryEntity implements Serializable {

    private String from;
    private String to;
    private double amount;
}
