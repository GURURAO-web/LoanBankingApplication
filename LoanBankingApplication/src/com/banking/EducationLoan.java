package com.banking;

public class EducationLoan extends Loan {

    public EducationLoan(double amount, int tenureMonths) {
        super(amount, tenureMonths);
    }

    @Override
    public double getAnnualInterestRate() {
        return 7.5;
    }

    @Override
    public String getLoanType() {
        return "Education Loan";
    }
}