package com.javastream.stock_visor.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String ticker;

    private String longTicker;

    private String instrumentId;

    private String baseUrl = "https://ssltvc.forexprostools.com";

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Stock() {
    }

    public Stock(String name,
                 String ticker,
                 String longTicker,
                 String instrumentId,
                 Portfolio portfolio) {
        this.name = name;
        this.ticker = ticker;
        this.longTicker = longTicker;
        this.instrumentId = instrumentId;
        this.portfolio = portfolio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getLongTicker() {
        return longTicker;
    }

    public void setLongTicker(String longTicker) {
        this.longTicker = longTicker;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(longTicker, stock.longTicker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longTicker);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortTicker='" + ticker + '\'' +
                ", longTicker='" + longTicker + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", portfolio=" + portfolio +
                '}';
    }
}