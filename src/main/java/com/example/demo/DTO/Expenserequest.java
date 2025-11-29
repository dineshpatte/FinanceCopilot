package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    @Getter
    @Setter
    private String note;

    public Expenserequest(String title, double amount, String category, String note) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.note = note;
    }
    public Expenserequest(){

    }



}
