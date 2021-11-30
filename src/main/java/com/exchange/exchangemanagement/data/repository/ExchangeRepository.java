package com.exchange.exchangemanagement.data.repository;

import com.exchange.exchangemanagement.data.model.ExchangeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRepository extends CrudRepository<ExchangeEntity, Integer> {

    @Query("SELECT u FROM ExchangeEntity u WHERE u.date = ?1")
    List<ExchangeEntity> listConvByDate(LocalDate date);
}
