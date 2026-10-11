package com.banking;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final BankOperations bank = new BankService();

    public static void main(String[] args) {

        int choice;

        System.out.println("==================================");
        System.out.println(" LOAN BANKING MANAGEMENT SYSTEM");
        System.out.println("==================================");

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {

                    case 1:
                        createCustomer();
                        break;

                    case 2:
                        applyForLoan();
                        break;

                    case 3:
                        approveLoan();
                        break;

                    case 4:
                        rejectLoan();
                        break;

                    case 5:
                        repayLoan();
                        break;

                    case 6:
                        bank.displayCustomers();
                        break;

                    case 7:
                        bank.displayApplications();
                        break;

                    case 8:
                        displayCustomerLoans();
                        break;

                    case 0:
                        System.out.println(
                            "Thank you for using our banking system."
                        );
                        break;

                    default:
                        System.out.println(
                            "Invalid choice. Try again."
                        );
                }

            } catch (BankException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);

        sc.close();
    }

    private static void displayMenu() {
        System.out.println("\n---------- MAIN MENU ----------");
        System.out.println("1. Register Customer");
        System.out.println("2. Apply for Loan");
        System.out.println("3. Approve Loan");
        System.out.println("4. Reject Loan");
        System.out.println("5. Repay Loan");
        System.out.println("6. View All Customers");
        System.out.println("7. View All Loan Applications");
        System.out.println("8. View Customer Loans");
        System.out.println("0. Exit");
        System.out.println("-------------------------------");
    }

    private static void createCustomer() {

        System.out.println("\n--- Customer Registration ---");

        System.out.print("Enter customer name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        Customer customer =
            bank.createCustomer(name, phone, email);

        System.out.println("\nCustomer registered successfully!");
        System.out.println(customer);
    }

    private static void applyForLoan() throws BankException {

        System.out.println("\n--- Loan Application ---");

        int customerId = readInt("Enter customer ID: ");

        System.out.println("1. Personal Loan (12% per year)");
        System.out.println("2. Home Loan (8.5% per year)");
        System.out.println("3. Education Loan (7.5% per year)");

        int type = readInt("Select loan type: ");

        double amount = readDouble("Enter loan amount: ");

        int tenure = readInt(
            "Enter tenure in months: "
        );

        double income = readDouble(
            "Enter monthly income: "
        );

        int creditScore = readInt(
            "Enter credit score (300-900): "
        );

        Loan loan;

        switch (type) {
            case 1:
                loan = new PersonalLoan(amount, tenure);
                break;

            case 2:
                loan = new HomeLoan(amount, tenure);
                break;

            case 3:
                loan = new EducationLoan(amount, tenure);
                break;

            default:
                throw new BankException(
                    "Invalid loan type selected."
                );
        }

        LoanApplication application =
            bank.applyForLoan(
                customerId, loan, income, creditScore
            );

        System.out.println("\nApplication submitted successfully!");
        System.out.println("Application ID: "
            + application.getApplicationId());
        System.out.println("Status: " + application.getStatus());
        System.out.printf("Estimated EMI: %.2f%n",
            loan.calculateEMI());

        System.out.println(
            "Your application is pending review."
        );
    }

    private static void approveLoan() throws BankException {

        int applicationId = readInt(
            "Enter application ID to approve: "
        );

        bank.approveLoan(applicationId);

        System.out.println(
            "Loan approved successfully!"
        );
    }

    private static void rejectLoan() throws BankException {

        int applicationId = readInt(
            "Enter application ID to reject: "
        );

        bank.rejectLoan(applicationId);

        System.out.println(
            "Loan application rejected."
        );
    }

    private static void repayLoan() throws BankException {

        int applicationId = readInt(
            "Enter application ID: "
        );

        double amount = readDouble(
            "Enter repayment amount: "
        );

        bank.repayLoan(applicationId, amount);

        System.out.println(
            "Repayment recorded successfully."
        );
    }

    private static void displayCustomerLoans()
            throws BankException {

        int customerId = readInt(
            "Enter customer ID: "
        );

        bank.displayCustomerLoans(customerId);
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a valid whole number."
                );
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (!Double.isFinite(value)) {
                    System.out.println(
                        "Please enter a finite number."
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a valid numeric value."
                );
            }
        }
    }
}