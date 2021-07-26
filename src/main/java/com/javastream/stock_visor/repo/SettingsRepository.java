package com.javastream.stock_visor.repo;

import com.javastream.stock_visor.model.Settings;
import com.javastream.stock_visor.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingsRepository extends CrudRepository<Settings, Integer> {
    List<Settings> findAll();
}
