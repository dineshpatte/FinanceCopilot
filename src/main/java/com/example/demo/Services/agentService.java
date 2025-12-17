package com.example.demo.Services;


import com.example.demo.Entities.Expense;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class agentService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FASTAPI_URL = "http://localhost:8000/analyze-expenses";


    public Map<String,Object> getExpenseSuggestions(List<Expense> expenses,int year,int month){
        try{
            double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();

            List<Map<String,Object>> cleanExpenses = expenses.stream().map((expense -> {
                Map<String,Object> expenseData = new HashMap<>();
                expenseData.put("id", expense.getId());
                expenseData.put("category", expense.getCategory());
                expenseData.put("amount",expense.getAmount());
                expenseData.put("description", expense.getNote());
                expenseData.put("date",expense.getDate());
                return expenseData;

            })).collect(Collectors.toList());

            Map<String,Object> request = new HashMap<>();
            request.put("year",year);
            request.put("month",month);
            request.put("totalAmount",totalAmount);

            request.put("expenses",cleanExpenses);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(request,headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL,requestEntity,Map.class);
            return response.getBody();

        }
        catch (Exception e){
            return Map.of(
                    "suggestions","AI service unavailable.please try later","error",e.getMessage()
            );
        }
    }

}
