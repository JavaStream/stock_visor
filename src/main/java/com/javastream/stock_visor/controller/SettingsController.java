package com.javastream.stock_visor.controller;

import com.javastream.stock_visor.model.ChartType;
import com.javastream.stock_visor.model.Interval;
import com.javastream.stock_visor.model.Settings;
import com.javastream.stock_visor.repo.SettingsRepository;
import com.javastream.stock_visor.service.SettingsService;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SettingsController {

    private final Logger logger = LoggerFactory.getLogger(SettingsController.class);

    private final String SETTINGS_MAIN                  = "/settings";
    private final String SETTINGS_CHART_SIZE            = "/settings/size";
    private final String SETTINGS_CHART_SIZE_UPDATE     = "/settings/size/add";
    private final String SETTINGS_CHART_INTERVAL        = "/settings/interval";
    private final String SETTINGS_CHART_INTERVAL_UPDATE = "/settings/interval/add";
    private final String SETTINGS_CHART_TYPE            = "/settings/chart-type";
    private final String SETTINGS_CHART_TYPE_UPDATE     = "/settings/chart-type/add";

    private final SettingsRepository settingsRepository;
    private final SettingsService settingsService;

    public SettingsController(SettingsRepository settingsRepository,
                              SettingsService settingsService) {
        this.settingsRepository = settingsRepository;
        this.settingsService = settingsService;
    }

    @GetMapping(SETTINGS_MAIN)
    public String main() {
        return "settings";
    }

    @GetMapping(SETTINGS_CHART_SIZE)
    public String chartSize(Model model) {
        Settings settings = settingsService.getSettings();

        model.addAttribute("settings", settings);
        return "settings_chart_size";
    }

    @PostMapping(SETTINGS_CHART_SIZE_UPDATE)
    public String updateChartSize(@Valid Settings settings,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "settings_chart_size";
        }

        Settings settingsDb = settingsService.getSettings();
        settingsDb.setChartHeight(settings.getChartHeight());
        settingsDb.setChartWidth(settings.getChartWidth());

        settingsRepository.save(settingsDb);
        logger.info("Settings was update");

        return "redirect:/settings";
    }

    @GetMapping(SETTINGS_CHART_INTERVAL)
    public String intervalMain(Model model) {
        Settings settings = settingsService.getSettings();

        model.addAttribute("settings", settings);
        model.addAttribute("intervals", Interval.values());
        return "settings_chart_interval";
    }

    @PostMapping(SETTINGS_CHART_INTERVAL_UPDATE)
    public String updateInterval(@RequestParam String interval) {

        Settings settingsDb = settingsService.getSettings();
        settingsDb.setInterval(Interval.getByValue(interval));
        settingsRepository.save(settingsDb);

        logger.info("Interval was update");
        return "redirect:/settings";
    }

    @GetMapping(SETTINGS_CHART_TYPE)
    public String chartTypeMain(Model model) {
        Settings settings = settingsService.getSettings();

        model.addAttribute("settings", settings);
        model.addAttribute("chartTypes", ChartType.values());
        return "settings_chart_type";
    }

    @PostMapping(SETTINGS_CHART_TYPE_UPDATE)
    public String updateChartType(@RequestParam String chartType) {

        Settings settingsDb = settingsService.getSettings();
        settingsDb.setChartType(ChartType.getByValue(chartType));
        settingsRepository.save(settingsDb);

        logger.info("ChartType was update");
        return "redirect:/settings";
    }
}