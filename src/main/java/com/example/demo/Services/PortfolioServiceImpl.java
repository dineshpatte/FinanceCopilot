package com.example.demo.Services;

import com.example.demo.DTO.PortfolioRequest;
import com.example.demo.DTO.WealthAdviceResponse;
import com.example.demo.Entities.Portfolio;
import com.example.demo.Entities.user;
import com.example.demo.Repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioServiceImpl implements PortfolioService {

    private PortfolioRepository portfolioRepository;
        private userService userService;
        private WealthAdvisorService wealthAdvisorService;


public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
    this.portfolioRepository = portfolioRepository;
    this.userService = userService;
    this.wealthAdvisorService = wealthAdvisorService;
}


    @Override
    public Portfolio createOrUpdtaePortfolio(PortfolioRequest request) {
        user  currentUser = userService.getCurrentUser();

        Portfolio portfolio = portfolioRepository.findByUserId(currentUser.getId())
                .orElseThrow(()-> new RuntimeException("Portfolio Not Found"));

        portfolio.setAge(request.getAge());
        portfolio.setMonthlyIncome(request.getMonthlyIncome());
        portfolio.setMonthlyExpenses(request.getMonthlyExpenses());

        portfolio.setSavingsAccount(request.getSavingsAccount());
        portfolio.setFixedDeposits(request.getFixedDeposits());
        portfolio.setMutualFunds(request.getMutualFunds());
        portfolio.setStocks(request.getStocks());
        portfolio.setGold(request.getGold());
        portfolio.setRealEstate(request.getRealEstate());
        portfolio.setEmergencyFund(request.getEmergencyFund());

        portfolio.setHomeLoan(request.getHomeLoan());
        portfolio.setCarLoan(request.getCarLoan());
        portfolio.setPersonalLoan(request.getPersonalLoan());
        portfolio.setCreditCardDebt(request.getCreditCardDebt());

        portfolio.setRiskProfile(request.getRiskProfile());
        portfolio.setRetirementGoal(request.getRetirementGoal());
        portfolio.setRetirementAge(request.getRetirementAge());

        portfolio.setHasHealthInsurance(request.getHasHealthInsurance());
        portfolio.setHasLifeInsurance(request.getHasLifeInsurance());
        portfolio.setMonthlyInvestment(request.getMonthlyInvestment());

        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolio() {
        user currentUser = userService.getCurrentUser();
        return portfolioRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found. Please create one first."));
    }

    @Override
    public WealthAdviceResponse getWealthAdvice() {
        Portfolio portfolio = getPortfolio();
        return wealthAdvisorService.generateWealthAdvisorService(portfolio);

    }




    @Override
    public void deletePortfolio() {

        user currentUser = userService.getCurrentUser();
        portfolioRepository.findByUserId(currentUser.getId())
                .ifPresent(portfolioRepository::delete);
    }
}
