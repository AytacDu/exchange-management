package com.exchange.exchangemanagement.data.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class ExchangeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    @Column
    private LocalDate date;

    @Column
    private String sourceCurrency;

    @Column
    private String targetCurrency;

    @Column
    private double sourceAmount;

    @Column
    private double targetAmount;

}
