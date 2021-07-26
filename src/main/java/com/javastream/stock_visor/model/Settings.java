package com.javastream.stock_visor.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = 300, message = "Высота должна быть не меньше 300")
    @Max(value = 500, message = "Высота должна быть не больше 500")
    private Integer chartHeight;

    @NotNull
    @Min(value = 300, message = "Ширина должна быть не меньше 300")
    @Max(value = 700, message = "Ширина должна быть не больше 700")
    private Integer chartWidth;

    @Enumerated(value = EnumType.STRING)
    private Interval interval;

    @Enumerated(value = EnumType.STRING)
    private ChartType chartType;

    public Settings() {
    }

    public Settings(Integer chartHeight,
                    Integer chartWidth,
                    Interval interval,
                    ChartType chartType) {
        this.chartHeight = chartHeight;
        this.chartWidth = chartWidth;
        this.interval = interval;
        this.chartType = chartType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChartHeight() {
        return chartHeight;
    }

    public void setChartHeight(Integer chartHeight) {
        this.chartHeight = chartHeight;
    }

    public Integer getChartWidth() {
        return chartWidth;
    }

    public void setChartWidth(Integer chartWidth) {
        this.chartWidth = chartWidth;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }
}
