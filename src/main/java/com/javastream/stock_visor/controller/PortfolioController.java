package com.javastream.stock_visor.controller;

import com.javastream.stock_visor.model.Portfolio;
import com.javastream.stock_visor.repo.PortfolioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
public class PortfolioController {

    private final String INDEX            = "/";
    private final String PORTFOLIOS       = "/portfolio";
    private final String PORTFOLIO_ADD    = "/portfolio/add";
    private final String PORTFOLIO_EDIT   = "/portfolio/edit/{id}";
    private final String PORTFOLIO_DELETE = "/portfolio/delete/{id}";

    private final PortfolioRepository portfolioRepository;

    public PortfolioController(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @GetMapping(value = { INDEX, PORTFOLIOS})
    public String index(Model model) {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        portfolios.sort(Comparator.comparingInt(Portfolio::getId));
        model.addAttribute("portfolioList", portfolios);
        return "portfolio";
    }

    @PostMapping(PORTFOLIO_ADD)
    public String addPortfolio(@RequestParam String name,
                               @RequestParam String description,
                               Model model) {
        Objects.requireNonNull(name, "portfolio name is null");
        Objects.requireNonNull(description, "portfolio description is null");

        Portfolio portfolio = new Portfolio(name, description);
        portfolioRepository.save(portfolio);
        return "redirect:/portfolio";
    }

    @GetMapping(PORTFOLIO_EDIT)
    public String editFormPortfolio(@PathVariable Integer id, Model model) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("portfolio ID is not correct"));
        model.addAttribute("portfolio", portfolio);
        return "editPortfolio";
    }

    @PostMapping(PORTFOLIO_EDIT)
    public String updatePortfolio(Portfolio portfolio) {
        Objects.requireNonNull(portfolio, "portfolio is null");

        portfolioRepository.save(portfolio);
        return "redirect:/portfolio";
    }

    @GetMapping(PORTFOLIO_DELETE)
    public String deletePortfolio(@PathVariable Integer id) {
        portfolioRepository.deleteById(id);

        return "redirect:/portfolio";
    }
}