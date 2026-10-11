package com.banking;

public abstract class Loan {

    private final double principal;
    private final int tenureMonths;
    private double outstandingBalance;

    public Loan(double principal, int tenureMonths) {
        if (!Double.isFinite(principal) || principal <= 0) {
            throw new IllegalArgumentException(
                "Loan amount must be greater than zero."
            );
        }

        if (tenureMonths <= 0) {
            throw new IllegalArgumentException(
                "Tenure must be greater than zero."
            );
        }

        this.principal = principal;
        this.tenureMonths = tenureMonths;
        this.outstandingBalance = principal;
    }

    public double getPrincipal() {
        return principal;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public abstract double getAnnualInterestRate();

    public abstract String getLoanType();

    public double calculateEMI() {
        double monthlyRate =
            getAnnualInterestRate() / (12 * 100);

        if (monthlyRate == 0) {
            return principal / tenureMonths;
        }

        double factor = Math.pow(
            1 + monthlyRate, tenureMonths
        );

        return principal * monthlyRate * factor
            / (factor - 1);
    }

    public double getTotalPayable() {
        return calculateEMI() * tenureMonths;
    }

    public void repay(double amount) throws BankException {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new BankException(
                "Repayment amount must be positive."
            );
        }

        if (outstandingBalance <= 0.001) {
            throw new BankException(
                "This loan has already been fully repaid."
            );
        }

        if (amount > outstandingBalance + 0.001) {
            throw new BankException(
                "Repayment exceeds outstanding balance: "
                + String.format("%.2f", outstandingBalance)
            );
        }

        outstandingBalance = Math.max(
            0, outstandingBalance - amount
        );
    }

    @Override
    public String toString() {
        return "Loan Type: " + getLoanType()
            + "\nPrincipal: " + String.format("%.2f", principal)
            + "\nAnnual Interest Rate: "
            + getAnnualInterestRate() + "%"
            + "\nTenure: " + tenureMonths + " months"
            + "\nMonthly EMI: "
            + String.format("%.2f", calculateEMI())
            + "\nEstimated Total Payable: "
            + String.format("%.2f", getTotalPayable())
            + "\nOutstanding Principal Balance: "
            + String.format("%.2f", outstandingBalance);
    }
}