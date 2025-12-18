package com.example.demo.DTO;


import com.example.demo.Entities.RiskProfile;
import lombok.Data;

@Data
public class PortfolioRequest {

    private Integer age;
    private Double monthlyIncome;
    private Double monthlyExpenses;

    // Current Portfolio
    private Double savingsAccount;
    private Double fixedDeposits;
    private Double mutualFunds;
    private Double stocks;
    private Double gold;
    private Double realEstate;
    private Double emergencyFund;

    // Debts
    private Double homeLoan;
    private Double carLoan;
    private Double personalLoan;
    private Double creditCardDebt;

    // Goals
    private RiskProfile riskProfile;
    private Double retirementGoal;
    private Integer retirementAge;

    // Insurance
    private Boolean hasHealthInsurance;
    private Boolean hasLifeInsurance;
    private Double monthlyInvestment;
}
