package com.banking;

public class HomeLoan extends Loan {

    public HomeLoan(double amount, int tenureMonths) {
        super(amount, tenureMonths);
    }

    @Override
    public double getAnnualInterestRate() {
        return 8.5;
    }

    @Override
    public String getLoanType() {
        return "Home Loan";
    }
}