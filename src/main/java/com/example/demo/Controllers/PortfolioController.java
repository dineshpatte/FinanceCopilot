package com.example.demo.Controllers;

import com.example.demo.DTO.PortfolioRequest;
import com.example.demo.DTO.WealthAdviceResponse;
import com.example.demo.Entities.Portfolio;
import com.example.demo.Services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<Portfolio> createOrUpdatePortfolio(@RequestBody PortfolioRequest request) {
        Portfolio portfolio = portfolioService.createOrUpdtaePortfolio(request);
        return ResponseEntity.ok(portfolio);
    }

    @GetMapping
    public ResponseEntity<Portfolio> getPortfolio() {
        Portfolio portfolio = portfolioService.getPortfolio();
        return ResponseEntity.ok(portfolio);
    }

    @GetMapping("/wealth-advice")
    public ResponseEntity<WealthAdviceResponse> getWealthAdvice() {
        WealthAdviceResponse advice = portfolioService.getWealthAdvice();
        return ResponseEntity.ok(advice);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePortfolio() {
        portfolioService.deletePortfolio();
        return ResponseEntity.ok("Portfolio deleted successfully");
    }
}