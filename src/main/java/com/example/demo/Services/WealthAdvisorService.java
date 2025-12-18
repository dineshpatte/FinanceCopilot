package com.example.demo.Services;


import com.example.demo.DTO.WealthAdviceResponse;
import com.example.demo.Entities.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WealthAdvisorService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String FASTAPI_URL = "http://localhost:8000/wealth-advice";

    public WealthAdviceResponse generateWealthAdvisorService(Portfolio portfolio)
    {
        try{
            double netWorth = portfolio.getNetWorth();
            double totalAssets = portfolio.getTotalAssets();
            double totalDebt = portfolio.getTotalDebt();
            double monthlySavings = portfolio.getMonthlySavings();

                Map<String ,Object> aiRequest = prepareAiRequest(portfolio);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(aiRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL, entity,Map.class);
            Map<String,Object> aiResponse = response.getBody();


            return WealthAdviceResponse.builder()
                    .netWorth(netWorth)
                    .totalAssets(totalAssets)
                    .totalDebt(totalDebt)
                    .monthlySavings(monthlySavings)
                    .aiAnalysis((String) aiResponse.get("aiAnalysis"))
                    .recommendedAllocation((Map<String, Double>) aiResponse.get("reccomendedAllocation"))
                    .actionItems((Map<String, String>) aiResponse.get("actionItems"))
                    .projectedWealth1Year((Double) aiResponse.get("projection1Year"))
                    .projectedWealth2Year((Double) aiResponse.get("projection2Year"))
                    .projectedWealth3Year((Double) aiResponse.get("projection3Year"))
                    .financialWealthScore((Integer) aiResponse.get("healthScore"))
                    .riskProfileAssessment((String) aiResponse.get("riskAssessment"))
                    .warnings(((List<String>) aiResponse.get("warnings")).toArray(new String[0]))
                    .opportunities(((List<String>) aiResponse.get("opportunities")).toArray(new String[0]))
                    .build();
        }

        catch(Exception e){
            System.err.println("Wealth Advisor Error: " + e.getMessage());
            return createFallbackResponse(portfolio);

        }
    }

    private Map<String,Object> prepareAiRequest(Portfolio portfolio)
    {
        Map<String,Object> request = new HashMap<>();
        request.put("age", portfolio.getAge());
        request.put("monthlyIncome", portfolio.getMonthlyIncome());
        request.put("monthlyExpenses", portfolio.getMonthlyExpenses());
        request.put("monthlySavings", portfolio.getMonthlySavings());

        Map<String, Double> currentAssets = new HashMap<>();
        currentAssets.put("savingsAccount", portfolio.getSavingsAccount());
        currentAssets.put("fixedDeposits", portfolio.getFixedDeposits());
        currentAssets.put("mutualFunds", portfolio.getMutualFunds());
        currentAssets.put("stocks", portfolio.getStocks());
        currentAssets.put("gold", portfolio.getGold());
        currentAssets.put("realEstate", portfolio.getRealEstate());
        currentAssets.put("emergencyFund", portfolio.getEmergencyFund());
        request.put("currentAssets", currentAssets);

        Map<String, Double> debts = new HashMap<>();
        debts.put("homeLoan", portfolio.getHomeLoan());
        debts.put("carLoan", portfolio.getCarLoan());
        debts.put("personalLoan", portfolio.getPersonalLoan());
        debts.put("creditCardDebt", portfolio.getCreditCardDebt());
        request.put("debts", debts);

        request.put("riskProfile", portfolio.getRiskProfile().toString());
        request.put("retirementGoal", portfolio.getRetirementGoal());
        request.put("retirementAge", portfolio.getRetirementAge());
        request.put("yearsToRetirement", portfolio.getRetirementAge() - portfolio.getAge());

        request.put("hasHealthInsurance", portfolio.getHasHealthInsurance());
        request.put("hasLifeInsurance", portfolio.getHasLifeInsurance());

        request.put("netWorth", portfolio.getNetWorth());
        request.put("totalAssets", portfolio.getTotalAssets());
        request.put("totalDebt", portfolio.getTotalDebt());

        return request;
    }


    private WealthAdviceResponse createFallbackResponse(Portfolio portfolio) {
        return WealthAdviceResponse.builder()
                .netWorth(portfolio.getNetWorth())
                .totalAssets(portfolio.getTotalAssets())
                .totalDebt(portfolio.getTotalDebt())
                .monthlySavings(portfolio.getMonthlySavings())
                .aiAnalysis("AI service is currently unavailable. Please try again later.")
                .financialWealthScore(50)
                .warnings(new String[]{"Unable to generate detailed analysis"})
                .opportunities(new String[]{"Please update your portfolio and try again"})
                .build();
    }
}
