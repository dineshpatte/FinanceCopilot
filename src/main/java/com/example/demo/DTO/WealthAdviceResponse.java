package com.example.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WealthAdviceResponse {
    private Double netWorth;
    private Double totalAssets;
    private Double totalDebt;
    private Double monthlySavings;


    private String aiAnalysis;
    private Map<String ,Double> recommendedAllocation;
    private Map<String,String> actionItems;


    private Double projectedWealth1Year;
    private Double projectedWealth2Year;
    private Double projectedWealth3Year;

    private Integer financialWealthScore;
    private String riskProfileAssessment;


    private String[] warnings;
    private String[] opportunities;

}
