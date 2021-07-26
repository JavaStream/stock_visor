package com.javastream.stock_visor.repo;

import com.javastream.stock_visor.model.Portfolio;
import com.javastream.stock_visor.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Integer> {
    List<Stock> findAll();
    List<Stock> findByPortfolio(Portfolio portfolio);
}
