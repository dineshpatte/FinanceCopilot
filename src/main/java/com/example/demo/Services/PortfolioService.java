package com.example.demo.Services;


import com.example.demo.DTO.PortfolioRequest;
import com.example.demo.DTO.WealthAdviceResponse;
import com.example.demo.Entities.Portfolio;

public interface PortfolioService {

    Portfolio createOrUpdtaePortfolio(PortfolioRequest request);
    Portfolio  getPortfolio();
    WealthAdviceResponse getWealthAdvice();
    void deletePortfolio();
}
