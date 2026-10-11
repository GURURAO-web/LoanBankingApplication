package com.banking;

public class LoanApplication {

    private final int applicationId;
    private final Customer customer;
    private final Loan loan;
    private final double monthlyIncome;
    private final int creditScore;

    private LoanStatus status;

    public LoanApplication(int applicationId,
                           Customer customer,
                           Loan loan,
                           double monthlyIncome,
                           int creditScore) {

        this.applicationId = applicationId;
        this.customer = customer;
        this.loan = loan;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
        this.status = LoanStatus.PENDING;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Loan getLoan() {
        return loan;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nApplication ID: " + applicationId
            + "\nCustomer: " + customer.getName()
            + "\nCustomer ID: " + customer.getCustomerId()
            + "\nLoan Type: " + loan.getLoanType()
            + "\nLoan Amount: "
            + String.format("%.2f", loan.getPrincipal())
            + "\nMonthly Income: "
            + String.format("%.2f", monthlyIncome)
            + "\nCredit Score: " + creditScore
            + "\nEMI: "
            + String.format("%.2f", loan.calculateEMI())
            + "\nStatus: " + status;
    }
}