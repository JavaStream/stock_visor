package com.javastream.stock_visor.repo;

import com.javastream.stock_visor.model.Portfolio;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {
    List<Portfolio> findAll();
}
