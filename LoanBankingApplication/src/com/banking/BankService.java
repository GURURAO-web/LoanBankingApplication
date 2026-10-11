package com.banking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BankService implements BankOperations {

    private final Map<Integer, Customer> customers =
        new LinkedHashMap<>();

    private final Map<Integer, LoanApplication> applications =
        new LinkedHashMap<>();

    private int nextCustomerId = 1001;
    private int nextApplicationId = 5001;

    @Override
    public Customer createCustomer(
            String name, String phone, String email) {

        Customer customer = new Customer(
            nextCustomerId++, name, phone, email
        );

        customers.put(customer.getCustomerId(), customer);

        return customer;
    }

    @Override
    public LoanApplication applyForLoan(
            int customerId,
            Loan loan,
            double monthlyIncome,
            int creditScore) throws BankException {

        Customer customer = customers.get(customerId);

        if (customer == null) {
            throw new BankException(
                "Customer ID not found."
            );
        }

        if (loan == null) {
            throw new BankException(
                "Loan details are required."
            );
        }

        if (!Double.isFinite(monthlyIncome)
                || monthlyIncome <= 0) {
            throw new BankException(
                "Monthly income must be positive."
            );
        }

        if (creditScore < 300 || creditScore > 900) {
            throw new BankException(
                "Credit score must be between 300 and 900."
            );
        }

        LoanApplication application =
            new LoanApplication(
                nextApplicationId++,
                customer,
                loan,
                monthlyIncome,
                creditScore
            );

        applications.put(
            application.getApplicationId(), application
        );

        return application;
    }

    @Override
    public void approveLoan(int applicationId)
            throws BankException {

        LoanApplication application =
            getApplication(applicationId);

        if (application.getStatus() != LoanStatus.PENDING) {
            throw new BankException(
                "Only pending applications can be approved."
            );
        }

        Loan loan = application.getLoan();

        boolean incomeEligible =
            application.getMonthlyIncome()
                >= loan.calculateEMI() * 3;

        boolean creditEligible =
            application.getCreditScore() >= 650;

        if (!incomeEligible || !creditEligible) {
            application.setStatus(LoanStatus.REJECTED);

            throw new BankException(
                "Loan rejected: eligibility criteria not met. "
                + "Required credit score: 650 and monthly income "
                + "at least 3 times the estimated EMI."
            );
        }

        application.setStatus(LoanStatus.APPROVED);
    }

    @Override
    public void rejectLoan(int applicationId)
            throws BankException {

        LoanApplication application =
            getApplication(applicationId);

        if (application.getStatus() != LoanStatus.PENDING) {
            throw new BankException(
                "Only pending applications can be rejected."
            );
        }

        application.setStatus(LoanStatus.REJECTED);
    }

    @Override
    public void repayLoan(int applicationId, double amount)
            throws BankException {

        LoanApplication application =
            getApplication(applicationId);

        if (application.getStatus() != LoanStatus.APPROVED) {
            throw new BankException(
                "Repayment requires an approved loan."
            );
        }

        application.getLoan().repay(amount);

        if (application.getLoan().getOutstandingBalance()
                <= 0.001) {
            application.setStatus(LoanStatus.CLOSED);
        }
    }

    private LoanApplication getApplication(int applicationId)
            throws BankException {

        LoanApplication application =
            applications.get(applicationId);

        if (application == null) {
            throw new BankException(
                "Loan application ID not found."
            );
        }

        return application;
    }

    @Override
    public void displayCustomers() {

        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }

        for (Customer customer : customers.values()) {
            System.out.println("------------------------");
            System.out.println(customer);
        }
    }

    @Override
    public void displayApplications() {

        if (applications.isEmpty()) {
            System.out.println("No loan applications found.");
            return;
        }

        for (LoanApplication application
                : applications.values()) {
            System.out.println("------------------------");
            System.out.println(application);

            if (application.getStatus() == LoanStatus.APPROVED
                    || application.getStatus() == LoanStatus.CLOSED) {
                System.out.println(
                    "Outstanding Balance: "
                    + String.format(
                        "%.2f",
                        application.getLoan()
                            .getOutstandingBalance()
                    )
                );
            }
        }
    }

    @Override
    public void displayCustomerLoans(int customerId)
            throws BankException {

        if (!customers.containsKey(customerId)) {
            throw new BankException("Customer ID not found.");
        }

        boolean found = false;

        for (LoanApplication application
                : applications.values()) {

            if (application.getCustomer().getCustomerId()
                    == customerId) {

                System.out.println("------------------------");
                System.out.println(application);
                System.out.println(application.getLoan());

                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "This customer has no loan applications."
            );
        }
    }
}