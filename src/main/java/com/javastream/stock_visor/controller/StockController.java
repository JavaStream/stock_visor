package com.javastream.stock_visor.controller;

import com.google.common.base.Preconditions;
import com.javastream.stock_visor.exception.ApiException;
import com.javastream.stock_visor.model.*;
import com.javastream.stock_visor.repo.PortfolioRepository;
import com.javastream.stock_visor.repo.SettingsRepository;
import com.javastream.stock_visor.repo.StockRepository;
import com.javastream.stock_visor.service.SettingsService;
import com.javastream.stock_visor.service.TickerService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class StockController {

    private final Logger logger = LoggerFactory.getLogger(StockController.class);

    private final String STOCK        = "/portfolio/{portfolioId}/stock";
    private final String STOCK_ADD    = "/portfolio/{portfolioId}/stock/add";
    private final String STOCK_EDIT   = "/portfolio/{portfolioId}/stock/edit/{stockId}";
    private final String STOCK_DELETE = "/portfolio/{portfolioId}/stock/delete/{stockId}";

    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final TickerService tickerService;
    private final SettingsService settingsService;

    public StockController(StockRepository stockRepository,
                           PortfolioRepository portfolioRepository,
                           TickerService tickerService,
                           SettingsRepository settingsRepository, SettingsService settingsService) {
        this.stockRepository = stockRepository;
        this.portfolioRepository = portfolioRepository;
        this.tickerService = tickerService;
        this.settingsService = settingsService;
    }

    @GetMapping(STOCK)
    public String main(@PathVariable Integer portfolioId,
                       Model model) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(IllegalStateException::new);

        List<Stock> stocks = stockRepository.findByPortfolio(portfolio);
        stocks.sort(Comparator.comparingInt(Stock::getId));

        Settings settings = settingsService.getSettings();

        model.addAttribute("portfolioId", portfolioId);
        model.addAttribute("intervals", Interval.values());
        model.addAttribute("newStock", new Stock());
        model.addAttribute("chartTypes", ChartType.values());
        model.addAttribute("stocks", stocks);
        model.addAttribute("settings", settings);
        return "stock";
    }

    @PostMapping(STOCK_ADD)
    public String addStock(@RequestParam String name,
                           @RequestParam String longTicker,
                           @PathVariable Integer portfolioId,
                           Model model) throws ApiException {
        Preconditions.checkState(!name.isBlank(), "stock name is null");
        Preconditions.checkState(!longTicker.isEmpty(), "stock longTicker is null");

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(IllegalStateException::new);

        Document document = tickerService.getDocument(longTicker);
        String ticker = tickerService.findTicker(document);
        String instrumentId = tickerService.findInstrumentId(document);

        Stock stock = new Stock(
                name,
                ticker,
                longTicker,
                instrumentId,
                portfolio
        );

        Stock savedStock = stockRepository.save(stock);
        logger.info("Stock was added: " + savedStock);

        return "redirect:/portfolio/" + portfolioId + "/stock";
    }

    @GetMapping(STOCK_EDIT)
    public String editFormStock(@PathVariable("stockId") Integer id,
                                Model model) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Stock ID is not correct"));
        model.addAttribute("stock", stock);
        return "editStock";
    }

    @PostMapping(STOCK_EDIT)
    public String updateStock(@RequestParam Integer id,
                              @RequestParam String name,
                              @RequestParam String longTicker,
                              @PathVariable Integer portfolioId
                              ) {
        Objects.requireNonNull(id, "stock id is null");
        Objects.requireNonNull(name, "stock name is null");
        Objects.requireNonNull(longTicker, "stock longTicker is null");

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Stock ID is not correct"));

        stock.setName(name);
        stock.setLongTicker(longTicker);
        Stock savedStock = stockRepository.save(stock);
        logger.info("Stock with id={} was save", savedStock.getId());
        return "redirect:/portfolio/" + portfolioId + "/stock";
    }

    @GetMapping(STOCK_DELETE)
    public String deleteStock(@PathVariable("stockId") Integer id,
                              @PathVariable("portfolioId") Integer portfolioId) {
        stockRepository.deleteById(id);
        logger.info("Stock with id={} was delete", id);
        return "redirect:/portfolio/" + portfolioId + "/stock";
    }

    @ExceptionHandler(ApiException.class)
    public String handleApiException(ApiException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}