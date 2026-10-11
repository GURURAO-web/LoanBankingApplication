package com.banking;

public class Customer {

    private final int customerId;
    private String name;
    private String phone;
    private String email;

    public Customer(int customerId, String name,
                    String phone, String email) {
        this.customerId = customerId;
        setName(name);
        setPhone(phone);
        setEmail(email);
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Customer name cannot be empty."
            );
        }
        this.name = name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Phone number cannot be empty."
            );
        }
        this.phone = phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException(
                "Enter a valid email address."
            );
        }
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId
            + "\nName: " + name
            + "\nPhone: " + phone
            + "\nEmail: " + email;
    }
}