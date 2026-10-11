package com.banking;

public interface BankOperations {

    Customer createCustomer(
        String name, String phone, String email
    );

    LoanApplication applyForLoan(
        int customerId,
        Loan loan,
        double monthlyIncome,
        int creditScore
    ) throws BankException;

    void approveLoan(int applicationId)
        throws BankException;

    void rejectLoan(int applicationId)
        throws BankException;

    void repayLoan(int applicationId, double amount)
        throws BankException;

    void displayCustomers();

    void displayApplications();

    void displayCustomerLoans(int customerId)
        throws BankException;
}