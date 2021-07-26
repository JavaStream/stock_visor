package com.javastream.stock_visor.service;

import com.javastream.stock_visor.model.ChartType;
import com.javastream.stock_visor.model.Interval;
import com.javastream.stock_visor.model.Settings;
import com.javastream.stock_visor.repo.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final int DEFAULT_HEIGHT = 500;
    private final int DEFAULT_WIDTH = 650;
    private final Interval DEFAULT_INTERVAL = Interval.MONTH;
    private final ChartType DEFAULT_CHART_TYPE = ChartType.CANDLES;

    private final SettingsRepository settingsRepository;

    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public Settings getSettings() {
        Settings settings = settingsRepository.findAll()
                .stream()
                .findFirst()
                .or(this::createDefaultSettings)
                .orElseThrow(IllegalStateException::new);

        if (Objects.isNull(settings.getChartHeight())) {
            settings.setChartHeight(DEFAULT_HEIGHT);
        }
        if (Objects.isNull(settings.getChartWidth())) {
            settings.setChartHeight(DEFAULT_WIDTH);
        }
        if (Objects.isNull(settings.getInterval())) {
            settings.setInterval(DEFAULT_INTERVAL);
        }
        if (Objects.isNull((settings.getChartType()))) {
            settings.setChartType(DEFAULT_CHART_TYPE);
        }

        return settings;
    }

    private Optional<Settings> createDefaultSettings() {
        if (settingsRepository.count() > 0) {
            settingsRepository.deleteAll();
        }

        Settings settings = new Settings(
                DEFAULT_HEIGHT,
                DEFAULT_WIDTH,
                DEFAULT_INTERVAL,
                DEFAULT_CHART_TYPE
        );
        return Optional.of(settingsRepository.save(settings));
    }
}