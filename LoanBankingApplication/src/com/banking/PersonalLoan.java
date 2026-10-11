package com.banking;

public class PersonalLoan extends Loan {

    public PersonalLoan(double amount, int tenureMonths) {
        super(amount, tenureMonths);
    }

    @Override
    public double getAnnualInterestRate() {
        return 12.0;
    }

    @Override
    public String getLoanType() {
        return "Personal Loan";
    }
}