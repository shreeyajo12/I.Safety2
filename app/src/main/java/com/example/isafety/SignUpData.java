package com.example.isafety;

public class SignUpData {
    String firstName,lastName,phoneNumber;
    String userType;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SignUpData(String firstName, String lastName, String userType, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
    }
}
