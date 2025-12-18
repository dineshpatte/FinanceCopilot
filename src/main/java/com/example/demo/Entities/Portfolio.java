package com.example.demo.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id",unique = true)
    private user user;



    private Integer age;
    private Double monthlyIncome;
    private Double monthlyExpenses;

    private Double savingsAccount;
    private Double fixedDeposits;
    private Double mutualFunds;
    private Double stocks;
    private Double gold;
    private Double realEstate;
    private Double emergencyFund;


    private Double homeLoan;
    private Double carLoan;
    private Double personalLoan;
    private Double creditCardDebt;


    @Enumerated(EnumType.STRING)
    private RiskProfile  riskProfile;

    private Double retirementGoal;
    private  Integer retirementAge;

    private Boolean hasHealthInsurance;
    private Boolean hasLifeInsurance;
    private Double monthlyInvestment;

    private LocalDate lastUpdated;
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        lastUpdated = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDate.now();
    }


    public Double getTotalAssets() {
        return (savingsAccount != null ? savingsAccount : 0) +
                (fixedDeposits != null ? fixedDeposits : 0) +
                (mutualFunds != null ? mutualFunds : 0) +
                (stocks != null ? stocks : 0) +
                (gold != null ? gold : 0) +
                (realEstate != null ? realEstate : 0) +

                (emergencyFund != null ? emergencyFund : 0);
    }

    public Double getTotalDebt() {
        return (homeLoan != null ? homeLoan : 0) +
                (carLoan != null ? carLoan : 0) +
                (personalLoan != null ? personalLoan : 0) +
                (creditCardDebt != null ? creditCardDebt : 0);
    }

    public Double getNetWorth() {
        return getTotalAssets() - getTotalDebt();
    }

    public Double getMonthlySavings() {
        return monthlyIncome - monthlyExpenses;
    }
}
