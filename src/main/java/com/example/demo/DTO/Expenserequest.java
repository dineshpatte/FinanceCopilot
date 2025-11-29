package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

public class Expenserequest {


    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private double amount;
    @Getter
    @Setter
    private String category;

    public Expenserequest(String title, double amount, String category) {
        this.title = title;
        this.amount = amount;
        this.category = category;
    }
    public Expenserequest(){

    }



}
